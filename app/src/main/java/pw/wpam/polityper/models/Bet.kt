package pw.wpam.polityper.models

data class Bet (
        val id: Int,
        val match: Match,
        val date: String,
        val playerOnePrediction: Int,
        val playerTwoPrediction: Int,
        val participant: Int
)