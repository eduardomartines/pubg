package pubg.domains

import pubg.services.pubgapi.PubgApiService
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class Summary @Inject constructor(private val pubgApiService: PubgApiService) {

    fun perform(playerNames: List<String>): SummaryResult {

        var apiCallCount = 0
        val playersResult = this.pubgApiService.players(playerNames).blockingGet()

        apiCallCount += 1

        val result = SummaryResult()

        for (playerData in playersResult.data) {
            result.playerAccountId = playerData.id

            for (matchData in playerData.relationships.matches.data) {
                result.matchIds.add(matchData.id)
            }
        }

        for (matchId in result.matchIds) {
            val matchResult = this.pubgApiService.match(matchId).blockingGet()
            apiCallCount += 1

            for (a in matchResult.data) {
                val date = a.attributes.createdAt
                val parsedDate = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)

                result.matchStartTimestamps.add(parsedDate)
            }

            for (i in matchResult.included) {
                val stats = i.attributes.stats

                if (stats != null) {
                    if (stats.kills != null && stats.playerId == result.playerAccountId) {
                        val killCount = stats.kills!!.toInt()

                        result.matchKills.add(killCount)
                        result.playerKillCount += killCount
                    }
                }
            }
        }

        return result
    }

}
