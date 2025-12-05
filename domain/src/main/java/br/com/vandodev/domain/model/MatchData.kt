package br.com.vandodev.domain.model

data class MatchData(
    val tournament: String,
    val timezone: String,
    val matches: List<Match>
)

data class Match(
    val match_id: Int,
    val home_team: String,
    val away_team: String,
    val date: String,
    val time: String,
    val stadium: String,
    val status: String,
    val score: Score
)

data class Score(
    val home: Int,
    val away: Int
)
