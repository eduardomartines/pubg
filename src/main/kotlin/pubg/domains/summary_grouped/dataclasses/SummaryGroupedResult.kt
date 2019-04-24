package pubg.domains.summary_grouped.dataclasses

data class SummaryGroupedResult(
    var _totalApiCallCount: Int = 0,
    var playerName: String? = null,
    var playerMatchesCount: Int = 0,
    var playerMatchesWonCount: Int = 0,
    var playerKillsCount: Int = 0,
    var playerEnemiesKnockedCount: Int = 0,
    var playerMissedKillsCount: Int = 0,
    var playerEnemiesDamageDealt: Double = 0.00,
    var playerKillsCountRatioByMatch: Double = 0.00,
    var playerRankAverageByMatch: Double = 0.00,
    var playerTeamRankAverageByMatch: Double = 0.00,
    var playerHeadshotKillsCount: Int = 0,
    var playerHeadshotKillsRatio: Double = 0.0,
    var playerAssistsCount: Int = 0,
    var playerRevivesCount: Int = 0,
    var playerFriendlyFireKillsCount: Int = 0,
    var datePeriodStart: String? = null,
    var datePeriodEnd: String? = null,
    var data: List<SummaryGroupedDataResult>? = null
)