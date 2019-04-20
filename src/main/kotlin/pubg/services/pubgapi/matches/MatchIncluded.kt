package pubg.services.pubgapi.matches

data class MatchIncluded(
    var type: String = "",
    var id: String = "",
    var attributes: MatchIncludedStats
)