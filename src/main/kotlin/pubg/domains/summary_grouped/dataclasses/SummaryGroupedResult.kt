package pubg.domains.summary_grouped.dataclasses

data class SummaryGroupedResult(
    var _totalApiCallCount: Int = 0,
    var playerMatchesCount: Int = 0,
    var playerMatchesWonCount: Int = 0,
    var playerKillsCount: Int = 0,
    var playerKillsCountAverageByMatch: Double = 0.00,
    var playerRankAverageByMatch: Double = 0.00,
    var data: List<SummaryGroupedDataResult>? = null
)