package pubg.services.pubgapi.matches

data class MatchIncludedStatsData(
    var DBNOs: String? = null,
    var assists: String? = null,
    var damageDealt: Float? = null,
    var headshotKills: Int? = null,
    var heals: Int? = null,
    var kills: Int? = null,
    var playerId: String? = null,
    var winPlace: Int? = null,
    var walkDistance: Float? = null,

    var rank: Int? = null,
    var teamId: Int? = null
)