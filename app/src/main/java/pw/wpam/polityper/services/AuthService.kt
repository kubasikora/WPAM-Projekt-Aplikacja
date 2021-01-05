package pw.wpam.polityper.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import pw.wpam.polityper.R
import pw.wpam.polityper.models.LoginResponse
import pw.wpam.polityper.models.RegistrationError
import pw.wpam.polityper.models.User
import java.nio.charset.StandardCharsets.UTF_8


object AuthService {
    val gson = Gson()
    private var context: Context? = null
    private var queue: RequestQueue? = null
    private val retryPolicy = DefaultRetryPolicy(
        60000,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )

    fun setContext(context: Context){
        this.context = context
        this.queue = Volley.newRequestQueue(this.context)
    }

    fun loginUser(username: String, password: String, complete: (Boolean, User?) -> Unit) {
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/auth/login/"

        val jsonBody = JSONObject()
        jsonBody.put("username", username)
        jsonBody.put("password", password)

        val loginRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                val login = gson.fromJson(response.toString(), LoginResponse::class.java)
                val sharedPref = this.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)!!
                with(sharedPref.edit()){
                    putString("jwt", login.token)
                    apply()
                }
                complete(true, login.user)
            },
            Response.ErrorListener { error ->
                Log.d("AUTH", error.toString())
                complete(false, null)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        loginRequest.retryPolicy = retryPolicy
        this.queue?.add(loginRequest)
    }

    fun updateProfile(firstName: String, lastName: String, complete: (Boolean, User?) -> Unit) {
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/auth/user/"

        val sharedPref = this.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)!!
        val token = sharedPref.getString("jwt", "None")

        val jsonBody = JSONObject()
        jsonBody.put("first_name", firstName)
        jsonBody.put("last_name", lastName)

        val updateRequest = object : JsonObjectRequest(Request.Method.PATCH, url, jsonBody,
            Response.Listener { response ->
                val userInfo = gson.fromJson(response.toString(), User::class.java)
                Log.d("AUTH", userInfo.toString())
                complete(true, userInfo)
            },
            Response.ErrorListener { error ->
                Log.d("AUTH", error.networkResponse.data.toString(UTF_8))
                complete(false, null)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        updateRequest.retryPolicy = retryPolicy
        this.queue?.add(updateRequest)
    }

    fun changePassword(password: String, password2: String, complete: (Boolean) -> Unit) {
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/auth/password/change/"

        val sharedPref = this.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)!!
        val token = sharedPref.getString("jwt", "None")

        val jsonBody = JSONObject()
        jsonBody.put("new_password1", password)
        jsonBody.put("new_password2", password2)

        val updateRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                complete(true)
            },
            Response.ErrorListener { error ->
                Log.d("AUTH", error.networkResponse.data.toString(UTF_8))
                complete(false)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        updateRequest.retryPolicy = retryPolicy
        this.queue?.add(updateRequest)
    }

    fun registerUser(email: String, username: String, password: String, password2: String, complete: (Boolean, String) -> Unit) {
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/auth/registration/"

        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("username", username)
        jsonBody.put("password1", password)
        jsonBody.put("password2", password2)

        val registrationRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                val registrationInfo = gson.fromJson(response.toString(), LoginResponse::class.java)
                Log.d("AUTH", registrationInfo.toString())
                complete(true, registrationInfo.user.username)
            },
            Response.ErrorListener { error ->
                Log.d("AUTH", error.networkResponse.data.toString(UTF_8))
                val errorResponse = gson.fromJson(error.networkResponse.data.toString(UTF_8), RegistrationError::class.java)
                var errorMessage: String = "There was a problem with your registration. Please try again later."
                if(errorResponse.otherErrors != null){
                    errorMessage = errorResponse.otherErrors[0]
                }
                if(errorResponse.passwordErrors != null){
                    errorMessage = errorResponse.passwordErrors[0]
                }
                if(errorResponse.usernameErrors != null){
                    errorMessage = errorResponse.usernameErrors[0]
                }
                if(errorResponse.emailErrors != null){
                    errorMessage = errorResponse.emailErrors[0]
                }
                complete(false, errorMessage)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        registrationRequest.retryPolicy = retryPolicy
        this.queue?.add(registrationRequest)
    }

    fun verifyToken(complete: (Boolean) -> Unit) {
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/auth/verify"

        val sharedPref = this.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)!!
        val token = sharedPref.getString("jwt", "None")

        val jsonBody = JSONObject()
        jsonBody.put("token", token)

        val verifyRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { _ -> complete(true) },
            Response.ErrorListener {  _->  complete(false) }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        verifyRequest.retryPolicy = retryPolicy
        this.queue?.add(verifyRequest)
    }

    fun logout(complete: (Boolean) -> Unit) {
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/auth/logout/"

        val sharedPref = this.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)!!
        val token = sharedPref.getString("jwt", "None")
        sharedPref.edit().remove("jwt").apply()

        val logoutRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener { _ ->
                Log.d("AUTH", "Logged out")
                complete(true) },
            Response.ErrorListener { _ -> complete(false) }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        logoutRequest.retryPolicy = retryPolicy
        this.queue?.add(logoutRequest)
    }

    fun getCurrentUser(complete: (Boolean, User?) -> Unit) {
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/auth/user/"

        val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)!!
        val token = sharedPref.getString("jwt", "None")

        val userRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val user = gson.fromJson(response.toString(), User::class.java)
                complete(true, user)
            },
            Response.ErrorListener { _ ->
                complete(false, null)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        userRequest.retryPolicy = retryPolicy
        this.queue?.add(userRequest)
    }
}