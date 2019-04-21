package pubg.domains.summary_grouped.dataclasses

import pubg.domains.summary.dataclasses.SummaryResult

data class SummaryGroupedDataResult(
    var summaryGroupedBy: String? = null,
    var summaryGroupedItem: String? = null,
    var summaryResult: SummaryResult? = null
)