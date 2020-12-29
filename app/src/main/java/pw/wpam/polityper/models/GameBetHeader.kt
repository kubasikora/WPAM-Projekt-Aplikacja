package pw.wpam.polityper.models

data class GameBetHeader (
        val firstContestantName: String,
        var secondContestantName: String,
        var firstContestantPoints: Int,
        var secondContestantPoints: Int,
        var firstContestantPrediction: Int,
        var secondContestantPrediction: Int
)