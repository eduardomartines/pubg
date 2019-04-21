package pubg.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import pubg.domains.Summary
import pubg.domains.SummaryResult
import javax.inject.Inject

@Controller("/summary")
class SummaryController @Inject constructor(private val summary: Summary) {

    @Get
    fun show(playerNames: List<String>): SummaryResult {
        return this.summary.perform(playerNames)
    }

}