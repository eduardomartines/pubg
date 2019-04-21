package pubg.domains.summary.dataclasses

import java.time.ZonedDateTime

data class SummaryResult(
    var apiCallCount: Int? = null,
    var playerAccountId: String? = null,
    var playerMatchRank: String? = null,
    var matchIds: MutableList<String> = ArrayList(),
    var matchKills: MutableList<Int> = ArrayList(),
    var matchStartFormattedTimestamps: MutableList<String> = ArrayList(),
    var matchStartTimestamps: MutableList<ZonedDateTime> = ArrayList(),
    var matchMapNames: MutableList<String> = ArrayList(),
    var playerKillCount: Int? = null
)
