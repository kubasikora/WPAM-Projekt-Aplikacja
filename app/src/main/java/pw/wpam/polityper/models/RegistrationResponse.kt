package pw.wpam.polityper.models

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
) {

}