package pubg.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Maybe
import pubg.services.pubgapi.PubgApiService
import pubg.services.pubgapi.dataclasses.players.PlayersResponse
import javax.inject.Inject

@Controller("/players")
class PlayersController @Inject constructor(private val pubgApiService: PubgApiService) {
    @Get
    fun list(playerNames: List<String>): Maybe<PlayersResponse> {
        return this.pubgApiService.players(playerNames)
    }
}