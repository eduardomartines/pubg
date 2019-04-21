package pubg.domains.summary.dataclasses

import java.time.ZonedDateTime

data class SummaryResult(
    var _apiCallCount: Int? = null,
    var playerAccountId: String = "",
    var playerKillCount: Int = 0,
    var matchesRanks: MutableList<Int> = ArrayList(),
    var matchesWon: MutableList<Int> = ArrayList(),
    var matchesIds: MutableList<String> = ArrayList(),
    var matchesKills: MutableList<Int> = ArrayList(),
    var matchesStartFormattedTimestamps: MutableList<String> = ArrayList(),
    var matchesStartTimestamps: MutableList<ZonedDateTime> = ArrayList(),
    var matchesMapNames: MutableList<String> = ArrayList(),
    var matchesGameModes: MutableList<String> = ArrayList()
)
