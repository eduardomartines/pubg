package pubg

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Maybe
import javax.inject.Inject

@Controller("/matches")
class MatchesController @Inject constructor(private val pubApiService: PubApiService) {

    @Get("/{matchId}")
    fun show(matchId: String): Maybe<MatchResponse> {

        return this.pubApiService.match(matchId)
    }

}