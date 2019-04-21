package pubg.services.pubgapi.dataclasses.matches

data class MatchIncludedStatsData(
    // participant's data
    var name: String? = null,
    var DBNOs: Int? = null,
    var assists: String? = null,
    var damageDealt: Double? = null,
    var headshotKills: Int? = null,
    var boosts: Int? = null,
    var heals: Int? = null,
    var kills: Int? = null,
    var revives: Int? = null,
    var playerId: String? = null,
    var winPlace: Int? = null,
    var walkDistance: Double? = null,

    // roster's data
    var rank: Int? = null,
    var teamId: Int? = null
)