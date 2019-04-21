package pubg.domains

import java.time.ZonedDateTime

data class SummaryResult(
    var playerAccountId: String? = null,
    var playerPosition: String? = null,
    var matchIds: MutableList<String> = ArrayList(),
    var matchKills: MutableList<Int> = ArrayList(),
    var matchStartTimestamps: MutableList<ZonedDateTime> = ArrayList(),
    var matchMapNames: MutableList<String> = ArrayList(),
    var playerKillCount: Int = 0
)