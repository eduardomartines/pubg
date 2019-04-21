package pubg.controllers

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import pubg.domains.summary_grouped.SummaryGroupedDomain
import pubg.domains.summary_grouped.dataclasses.SummaryGroupedResult
import javax.inject.Inject

@Controller("/summary_grouped")
class SummaryGroupedController @Inject constructor(
    private val summaryGroupedDomain: SummaryGroupedDomain
) {
    @Get
    fun show(
        playerNames: List<String>,
        groupBy: String,
        limitByDaysCount: Int?,
        limitByMatchCount: Int?,
        filterByMatchGameMode: String?
    ): SummaryGroupedResult {
        return this.summaryGroupedDomain.perform(
            playerNames,
            groupBy,
            limitByDaysCount,
            limitByMatchCount,
            filterByMatchGameMode
        )
    }
}