package pw.wpam.polityper.models

data class League(
        val id: Int,
        val tournament: Tournament,
        val name: String,
        val created: String,
        val leagueKey: String
)
