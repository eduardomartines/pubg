package pubg.domains.summary_grouped

import pubg.domains.summary.SummaryDomain
import pubg.domains.summary.dataclasses.SummaryResult
import pubg.domains.summary_grouped.dataclasses.SummaryGroupedDataResult
import pubg.domains.summary_grouped.dataclasses.SummaryGroupedResult
import java.time.ZonedDateTime
import javax.inject.Inject
import kotlin.math.absoluteValue

class SummaryGroupedDomain @Inject constructor(private val summaryDomain: SummaryDomain) {
    private companion object {
        enum class GroupByOptions{
            BY_DAYS_COUNT
        }
    }

    fun perform(
        playerNames: List<String>,
        groupBy: String,
        limitByDaysCount: Int?,
        limitByMatchCount: Int?
    ): SummaryGroupedResult {
        val groupByOption = GroupByOptions.valueOf(groupBy)
        val summary = this.summaryDomain.perform(playerNames, limitByDaysCount, limitByMatchCount)
        val data: MutableList<SummaryGroupedDataResult> = ArrayList()
        var totalMatchCount = 0
        var totalPlayerKillsCount = 0

        for (dayIndex in (limitByDaysCount!!.toInt() - 1) downTo 0) {
            val nSummaryResult = SummaryResult(playerKillCount = 0)

            for (index in summary.matchIds.lastIndex downTo 0) {
                val dayIndexFromToday = (
                    summary.matchStartTimestamps[index].dayOfMonth - ZonedDateTime.now().dayOfMonth
                ).absoluteValue

                if (dayIndexFromToday == dayIndex) {
                    val matchKills = summary.matchKills[index]

                    totalMatchCount += 1
                    totalPlayerKillsCount += matchKills

                    nSummaryResult.playerKillCount = nSummaryResult.playerKillCount!! + matchKills
                    nSummaryResult.matchKills.add(matchKills)
                    nSummaryResult.matchIds.add(summary.matchIds[index])
                    nSummaryResult.matchStartFormattedTimestamps.add(
                        summary.matchStartFormattedTimestamps[index]
                    )
                }
            }

            data.add(
                SummaryGroupedDataResult(
                    summaryGroupedBy = groupByOption.name,
                    summaryGroupedItem = dayIndex.toString(),
                    summaryResult = nSummaryResult
                )
            )
        }

        var totalPlayerAverageKillByMatch = totalPlayerKillsCount / totalMatchCount.toDouble()
        totalPlayerAverageKillByMatch = Math.round(totalPlayerAverageKillByMatch * 100.0) / 100.0

        return SummaryGroupedResult(
            _totalApiCallCount = summary.apiCallCount,
            totalMatchesCount = totalMatchCount,
            totalPlayerKillsCount = totalPlayerKillsCount,
            totalPlayerAverageKillsByMatch = totalPlayerAverageKillByMatch,
            data = data
        )
    }
}