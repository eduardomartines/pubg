package pubg.domains.summary

import pubg.domains.summary.dataclasses.SummaryResult
import pubg.services.pubgapi.PubgApiService
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.math.absoluteValue

class SummaryDomain @Inject constructor(private val pubgApiService: PubgApiService) {
    companion object {
        const val TIMESTAMP_FORMAT = "dd/MM/yyyy - hh:mm"
    }

    fun perform(
        playerNames: List<String>,
        limitByDaysCount: Int?,
        limitByMatchCount: Int?
    ): SummaryResult {
        var apiCallCount = 0

        val playersResult = this.pubgApiService.players(playerNames).blockingGet()
        apiCallCount += 1

        val result = SummaryResult(playerKillCount = 0)
        val matchIds: MutableList<String> = ArrayList()

        for (playerData in playersResult.data) {
            result.playerAccountId = playerData.id

            for (matchData in playerData.relationships.matches.data) {
                matchIds.add(matchData.id!!)
            }
        }

        for (matchId in matchIds) {

            if (limitByMatchCount != null && limitByMatchCount == result.matchIds.size) {
                break
            }

            if (limitByDaysCount != null && result.matchStartTimestamps.size != 0) {
                val dayOfMonth = result.matchStartTimestamps.last().dayOfMonth
                val todayDayOfMouth = ZonedDateTime.now().dayOfMonth
                val diff = (todayDayOfMouth - dayOfMonth).absoluteValue

                if (diff >= limitByDaysCount) {
                    break
                }
            }

            result.matchIds.add(matchId)

            val matchResult = this.pubgApiService.match(matchId).blockingGet()
            apiCallCount += 1

            for (matchData in matchResult.data) {
                val date = matchData.attributes.createdAt
                val parsedDate = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)

                result.matchStartTimestamps.add(parsedDate)
                result.matchStartFormattedTimestamps.add(
                    DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT).format(parsedDate)
                )
            }

            for (matchIncludedData in matchResult.included) {
                val stats = matchIncludedData.attributes.stats ?: continue

                if (stats.kills == null || stats.playerId != result.playerAccountId) {
                    continue
                }

                val killCount = stats.kills!!.toInt()

                result.matchKills.add(killCount)
                result.playerKillCount = result.playerKillCount!! + killCount
            }
        }

        result.apiCallCount = apiCallCount

        return result
    }
}
