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

        enum class FilterByMatchGameModeOptions(val value: String){
            SQUAD_FPP("squad-fpp")
        }
    }

    fun perform(
        playerNames: List<String>,
        groupBy: String,
        limitByDaysCount: Int?,
        limitByMatchCount: Int?,
        filterByMatchGameMode: String?
    ): SummaryGroupedResult {
        val groupByOption = GroupByOptions.valueOf(groupBy)

        var filterByMatchGameModeOption: String? = null

        if (filterByMatchGameMode != null) {
            filterByMatchGameModeOption = FilterByMatchGameModeOptions.valueOf(
                filterByMatchGameMode
            ).value
        }

        val summaryResult = this.summaryDomain.perform(
            playerNames,
            limitByDaysCount,
            limitByMatchCount
        )

        val data: MutableList<SummaryGroupedDataResult> = ArrayList()
        var totalMatchCount = 0
        var totalPlayerKillsCount = 0

        for (dayIndex in (limitByDaysCount!!.toInt() - 1) downTo 0) {
            val dayIndexSummaryResult = SummaryResult()

            for (index in summaryResult.matchesIds.lastIndex downTo 0) {
                val dayIndexFromToday = (
                    summaryResult.matchesStartTimestamps[index].dayOfMonth
                        - ZonedDateTime.now().dayOfMonth
                ).absoluteValue

                if (dayIndexFromToday == dayIndex) {
                    val matchKills = summaryResult.matchesKills[index]

                    totalMatchCount += 1
                    totalPlayerKillsCount += matchKills

                    dayIndexSummaryResult.playerKillCount += matchKills
                    dayIndexSummaryResult.matchesKills.add(matchKills)
                    dayIndexSummaryResult.matchesIds.add(summaryResult.matchesIds[index])
                    dayIndexSummaryResult.matchesMapNames.add(summaryResult.matchesMapNames[index])
                    dayIndexSummaryResult.matchesRanks.add(summaryResult.matchesRanks[index])
                    dayIndexSummaryResult.matchesWon.add(summaryResult.matchesWon[index])

                    val matchGameMode = summaryResult.matchesGameModes[index]

                    if (filterByMatchGameModeOption == null ||
                            filterByMatchGameModeOption == matchGameMode) {
                        dayIndexSummaryResult.matchesGameModes.add(matchGameMode)
                    }

                    dayIndexSummaryResult.matchesStartFormattedTimestamps.add(
                        summaryResult.matchesStartFormattedTimestamps[index]
                    )
                }
            }

            data.add(
                SummaryGroupedDataResult(
                    summaryGroupedBy = groupByOption.name,
                    summaryGroupedItem = dayIndex.toString(),
                    summaryResult = dayIndexSummaryResult
                )
            )
        }

        var totalPlayerAverageKillByMatch = totalPlayerKillsCount / totalMatchCount.toDouble()
        totalPlayerAverageKillByMatch = Math.round(totalPlayerAverageKillByMatch * 100.0) / 100.0

        var totalPlayerAverageRanksByMatch = summaryResult.matchesRanks.sum() /
            totalMatchCount.toDouble()
        totalPlayerAverageRanksByMatch = Math.round(totalPlayerAverageRanksByMatch * 100.0) / 100.0

        return SummaryGroupedResult(
            _totalApiCallCount = summaryResult._apiCallCount!!,
            playerName = summaryResult.playerName,
            playerMatchesCount = totalMatchCount,
            playerKillsCount = totalPlayerKillsCount,
            playerKillsCountAverageByMatch = totalPlayerAverageKillByMatch,
            playerRankAverageByMatch = totalPlayerAverageRanksByMatch,
            playerMatchesWonCount = summaryResult.matchesWon.sum(),
            data = data
        )
    }
}