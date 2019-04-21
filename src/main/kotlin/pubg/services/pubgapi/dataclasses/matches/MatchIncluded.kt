package pubg.services.pubgapi.dataclasses.matches

data class MatchIncluded(
    var type: String? = null,
    var id: String? = null,
    var attributes: MatchIncludedStats,
    var relationships: MatchIncludedRosterRelationships? = null
)