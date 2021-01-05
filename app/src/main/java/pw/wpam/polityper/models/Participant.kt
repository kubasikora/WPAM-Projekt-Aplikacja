package pw.wpam.polityper.models

data class Participant(
    val id : Int,
    val user: User,
    val league: League,
    val points: Int,
    val joined: String
)