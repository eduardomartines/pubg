package pubg.services.pubgapi.dataclasses.matches

data class MatchResponse(
    var data: List<MatchData>,
    var included: List<MatchIncluded>
)