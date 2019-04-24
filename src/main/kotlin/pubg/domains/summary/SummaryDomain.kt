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
        var playerParticipantId = ""

        val playersResult = this.pubgApiService.players(playerNames).blockingGet()
        apiCallCount += 1

        val summaryResult = SummaryResult()
        val matchIds: MutableList<String> = ArrayList()

        for (playerData in playersResult.data) {
            summaryResult.playerAccountId = playerData.id!!

            for (matchData in playerData.relationships.matches.data) {
                matchIds.add(matchData.id!!)
            }
        }

        for (matchId in matchIds) {

            if (limitByMatchCount != null && limitByMatchCount == summaryResult.matchesIds.size) {
                break
            }

            if (limitByDaysCount != null && summaryResult.matchesStartTimestamps.size != 0) {
                val dayOfMonth = summaryResult.matchesStartTimestamps.last().dayOfMonth
                val todayDayOfMouth = ZonedDateTime.now().dayOfMonth
                val diff = (todayDayOfMouth - dayOfMonth).absoluteValue

                if (diff >= limitByDaysCount) {
                    break
                }
            }

            summaryResult.matchesIds.add(matchId)

            val matchResult = this.pubgApiService.match(matchId).blockingGet()
            apiCallCount += 1

            for (matchData in matchResult.data) {
                val date = matchData.attributes.createdAt
                val parsedDate = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)

                summaryResult.matchesStartTimestamps.add(parsedDate)
                summaryResult.matchesStartFormattedTimestamps.add(
                    DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT).format(parsedDate)
                )
                summaryResult.matchesMapNames.add(matchData.attributes.mapName!!)
                summaryResult.matchesGameModes.add(matchData.attributes.gameMode!!)
            }

            for (matchIncludedData in matchResult.included) {
                if (matchIncludedData.type != "participant") {
                    continue
                }

                val stats = matchIncludedData.attributes.stats

                if (stats!!.playerId != summaryResult.playerAccountId) {
                    continue
                }

                playerParticipantId = matchIncludedData.id!!

                val killCount = stats.kills!!.toInt()

                summaryResult.matchesAssists.add(stats.assists!!)
                summaryResult.matchesRevives.add(stats.revives!!)
                summaryResult.matchesFriendlyFireKills.add(stats.teamKills!!)
                summaryResult.matchesHeadshotKills.add(stats.headshotKills!!)
                summaryResult.matchesEnemiesKnockedCount.add(stats.DBNOs!!)
                summaryResult.matchesEnemiesDamageDealt.add(stats.damageDealt!!)
                summaryResult.matchesPlayerRanks.add(stats.winPlace!!)
                summaryResult.matchesKills.add(killCount)
                summaryResult.playerKillCount += killCount
                summaryResult.playerName = stats.name
            }

            for (matchIncludedData in matchResult.included) {
                if (matchIncludedData.type == "roster") {

                    for (data in matchIncludedData.relationships!!.participants!!.data!!) {
                        if (data.id != playerParticipantId) {
                            continue
                        }

                        if (matchIncludedData.attributes.won == "true") {
                            summaryResult.matchesWon.add(1)
                        } else {
                            summaryResult.matchesWon.add(0)
                        }

                        summaryResult.matchesTeamRanks.add(
                            matchIncludedData.attributes.stats!!.rank!!
                        )
                    }
                }
            }
        }

        summaryResult._apiCallCount = apiCallCount

        return summaryResult
    }
}
