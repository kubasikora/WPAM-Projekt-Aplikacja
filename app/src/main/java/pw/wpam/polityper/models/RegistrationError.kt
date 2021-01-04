package pw.wpam.polityper.models

import com.google.gson.annotations.SerializedName

data class RegistrationError(
    @SerializedName("username") val usernameErrors: Array<String>?,
    @SerializedName("email") val emailErrors: Array<String>?,
    @SerializedName("password1") val passwordErrors: Array<String>?,
    @SerializedName("non_field_errors") val otherErrors: Array<String>?
) {
    override fun equals(other: Any?): Boolean {
        return true
    }

    override fun hashCode(): Int {
        return 0
    }
}