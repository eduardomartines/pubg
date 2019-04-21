package pubg.domains.summary_grouped.dataclasses

data class SummaryGroupedResult(
    var _totalApiCallCount: Int? = null,
    var totalMatchesCount: Int? = null,
    var totalPlayerKillsCount: Int? = null,
    var totalPlayerAverageKillsByMatch: Double? = null,
    var data: List<SummaryGroupedDataResult>? = null
)