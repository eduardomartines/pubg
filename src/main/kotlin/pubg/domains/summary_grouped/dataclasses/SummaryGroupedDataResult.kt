package pubg.domains.summary_grouped.dataclasses

import pubg.domains.summary.dataclasses.SummaryResult

data class SummaryGroupedDataResult(
    var summaryGroupedBy: String = "",
    var summaryGroupedItem: String = "",
    var summaryResult: SummaryResult? = null
)