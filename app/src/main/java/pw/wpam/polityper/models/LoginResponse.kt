package pw.wpam.polityper.models

import com.google.gson.annotations.SerializedName
import pw.wpam.polityper.models.User

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: User
) {
}