package pubg.services.pubgapi.dataclasses.matches

data class MatchAttributes(
    var mapName: String? = null,
    var gameMode: String? = null,
    var createdAt: String? = null,
    var duration: Int? = 0
)