package pw.wpam.polityper.models

data class Match(
        val id: Int,
        val playerOne: Contestant,
        val playerTwo: Contestant,
        val tournament: Tournament,
        val venue: Venue?,
        val dateOfStart: String,
        val sport: String,
        val playerOneResult: Int,
        val playerTwoResult: Int,
        val outcome: String,
        val finished: Boolean
)