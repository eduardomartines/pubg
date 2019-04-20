package pubg

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpAttributes
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriTemplate
import io.reactivex.Maybe
import javax.inject.Inject
import javax.inject.Singleton
import javax.management.relation.Relation

@Singleton
class PubApiService @Inject constructor(

    @param:Client(PubgConfiguration.URL)
    private val httpClient: RxHttpClient,

    private val pubConfiguration: PubgConfiguration

) {

    fun players(playerNames: List<String>): Maybe<PlayersResponse> {
        val names = playerNames.joinToString(separator = ",")
        val path = "/shards/steam/players?filter[playerNames]=$names"

        val request = HttpRequest.GET<Any>(path)
        request.header("Authorization", this.pubConfiguration.key)
        request.header("Accept", "application/vnd.api+json")

        val flowable = httpClient.retrieve(
            request,
            Argument.of(PlayersResponse::class.java)
        )

        return flowable.firstElement() as Maybe<PlayersResponse>
    }

    fun match(matchId: String): Maybe<MatchResponse> {
        val path = "/shards/steam/matches/$matchId"

        val request = HttpRequest.GET<Any>(path)
        request.header("Authorization", this.pubConfiguration.key)
        request.header("Accept", "application/vnd.api+json")

        val flowable = httpClient.retrieve(
            request,
            Argument.of(MatchResponse::class.java)
        )

        return flowable.firstElement() as Maybe<MatchResponse>
    }

}


data class PlayersResponse(
    var data: List<PlayersData>
)

data class PlayersData(
    var id: String = "",
    var relationships: PlayersRelationships
)

data class PlayersRelationships(
    var matches: MatchesData
)

data class MatchesData(
    var data: List<PlayersMatchData>
)

data class PlayersMatchData(
    var id: String = ""
)

//

data class MatchResponse(
    var data: List<MatchData>,
    var included: List<MatchIncluded>
)

data class MatchData(
    var attributes: MatchAttributes
)

data class MatchAttributes(
    var createdAt: String = "null",
    var duration: Int? = 0
)

data class MatchIncluded(
    var type: String = "",
    var id: String = "",
    var attributes: MatchIncludedStats
)

data class MatchIncludedStats(
    var stats: MatchIncludedStatsData? = null,
    var won: String? = null
)

data class MatchIncludedStatsData(
    var DBNOs: String? = null,
    var assists: String? = null,
    var damageDealt: Float? = null,
    var headshotKills: Int? = null,
    var heals: Int? = null,
    var kills: Int? = null,
    var playerId: String? = null,
    var winPlace: Int? = null,
    var walkDistance: Float? = null,

    var rank: Int? = null,
    var teamId: Int? = null
)