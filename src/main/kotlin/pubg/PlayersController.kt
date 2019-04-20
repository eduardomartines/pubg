package pubg

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.Maybe
import javax.inject.Inject

@Controller("/players")
class PlayersController @Inject constructor(private val pubApiService: PubApiService) {

    @Get
    fun list(playerNames: List<String>): Maybe<PlayersResponse> {

        return this.pubApiService.players(playerNames)
    }

}