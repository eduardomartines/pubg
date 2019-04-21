package pubg.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Maybe
import pubg.services.pubgapi.PubgApiService
import pubg.services.pubgapi.dataclasses.matches.MatchResponse
import javax.inject.Inject

@Controller("/matches")
class MatchesController @Inject constructor(private val pubgApiService: PubgApiService) {
    @Get("/{matchId}")
    fun show(matchId: String): Maybe<MatchResponse> {
        return this.pubgApiService.match(matchId)
    }
}