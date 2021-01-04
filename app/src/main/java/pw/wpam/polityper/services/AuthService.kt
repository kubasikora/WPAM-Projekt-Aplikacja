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
import pw.wpam.polityper.models.User


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
        val urlBase = this.context!!.getString(R.string.be_url)
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

    fun verifyToken(complete: (Boolean) -> Unit) {
        val urlBase = this.context!!.getString(R.string.be_url)
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
        val urlBase = this.context!!.getString(R.string.be_url)
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
        val urlBase = this.context!!.getString(R.string.be_url)
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