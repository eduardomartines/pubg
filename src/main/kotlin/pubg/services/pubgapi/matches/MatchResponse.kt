package pubg.services.pubgapi.matches

data class MatchResponse(
    var data: List<MatchData>,
    var included: List<MatchIncluded>
)