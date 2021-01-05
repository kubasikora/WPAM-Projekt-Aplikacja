package pw.wpam.polityper.models

import android.media.Image

data class League(
        val id: Int,
        val tournament: Tournament,
        val name: String,
        val created: String,
        val leagueKey: String
)
