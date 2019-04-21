package pubg.services.pubgapi

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Maybe
import pubg.configs.PubgConfiguration
import pubg.services.pubgapi.dataclasses.matches.MatchResponse
import pubg.services.pubgapi.dataclasses.players.PlayersResponse
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class PubgApiService @Inject constructor(
    @Client(id = PubgConfiguration.PREFIX)
    private val httpClient: RxHttpClient,

    private val pubConfiguration: PubgConfiguration
) {
    fun players(playerNames: List<String>): Maybe<PlayersResponse> {
        val names = playerNames.joinToString(separator = ",")

        return this.perform(
            String.format(this.pubConfiguration.playersUrl!!, names),
            PlayersResponse::class.java
        ) as Maybe<PlayersResponse>
    }

    fun match(matchId: String): Maybe<MatchResponse> {
        return this.perform(
            String.format(this.pubConfiguration.matchUrl!!, matchId),
            MatchResponse::class.java
        ) as Maybe<MatchResponse>
    }

    private fun perform(path: String, responseClass: Class<*>): Maybe<*>? {
        val request = HttpRequest.GET<Any>("${this.pubConfiguration.url}$path")
            .header("Authorization", this.pubConfiguration.key)
            .header("Accept", "application/vnd.api+json")

        return httpClient.retrieve(request, Argument.of(responseClass)).firstElement()
    }
}
