package pubg.services.pubgapi.dataclasses.matches

data class MatchIncludedStatsData(
    // participant's data
    var DBNOs: String? = null,
    var assists: String? = null,
    var damageDealt: Float? = null,
    var headshotKills: Int? = null,
    var heals: Int? = null,
    var kills: Int? = null,
    var playerId: String? = null,
    var winPlace: Int? = null,
    var walkDistance: Double? = null,

    // roster's data
    var rank: Int? = null,
    var teamId: Int? = null
)