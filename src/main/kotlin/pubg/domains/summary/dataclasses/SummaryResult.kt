package pubg.domains.summary.dataclasses

import java.time.ZonedDateTime

data class SummaryResult(
    var _apiCallCount: Int? = null,
    var playerName: String? = null,
    var playerAccountId: String? = null,
    var playerKillCount: Int = 0,
    var matchesPlayerRanks: MutableList<Int> = ArrayList(),
    var matchesTeamRanks: MutableList<Int> = ArrayList(),
    var matchesWon: MutableList<Int> = ArrayList(),
    var matchesIds: MutableList<String> = ArrayList(),
    var matchesKills: MutableList<Int> = ArrayList(),
    var matchesEnemiesDamageDealt: MutableList<Double> = ArrayList(),
    var matchesEnemiesKnockedCount: MutableList<Int> = ArrayList(),
    var matchesStartFormattedTimestamps: MutableList<String> = ArrayList(),
    var matchesStartTimestamps: MutableList<ZonedDateTime> = ArrayList(),
    var matchesMapNames: MutableList<String> = ArrayList(),
    var matchesGameModes: MutableList<String> = ArrayList(),
    var matchesHeadshotKills: MutableList<Int> = ArrayList(),
    var matchesAssists: MutableList<Int> = ArrayList(),
    var matchesRevives: MutableList<Int> = ArrayList(),
    var matchesFriendlyFireKills: MutableList<Int> = ArrayList()
)
