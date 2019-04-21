package pubg.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import pubg.domains.summary.SummaryDomain
import pubg.domains.summary.dataclasses.SummaryResult
import javax.inject.Inject

@Controller("/summary")
class SummaryController @Inject constructor(private val summaryDomain: SummaryDomain) {
    @Get
    fun show(
        playerNames: List<String>,
        limitByDaysCount: Int?,
        limitByMatchCount: Int?
    ): SummaryResult {
        return this.summaryDomain.perform(playerNames, limitByDaysCount, limitByMatchCount)
    }
}