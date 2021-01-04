package pw.wpam.polityper.models

import com.google.gson.annotations.SerializedName

data class Contestant(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sport") val sport: String
) {
}