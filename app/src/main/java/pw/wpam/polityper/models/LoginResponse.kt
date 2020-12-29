package pw.wpam.polityper.models

import pw.wpam.polityper.models.User

data class LoginResponse(
    val token: String,
    val user: User
) {
}