package pw.wpam.polityper.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pw.wpam.polityper.R
import pw.wpam.polityper.models.LoginResponse

object AuthService {
    val gson = Gson()

    fun loginUser(context: Context, username: String, password: String, complete: (Boolean) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val urlBase = context.getString(R.string.be_url)
        val url = "${urlBase}/auth/login/"

        val jsonBody = JSONObject()
        jsonBody.put("username", username)
        jsonBody.put("password", password)

        val loginRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                val login = gson.fromJson(response.toString(), LoginResponse::class.java)
                Log.d("INFO", login.token)
                Log.d("INFO", login.user.id.toString())
                val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
                with(sharedPref.edit()){
                    putString("jwt", login.token)
                    apply()
                }
                complete(true)
            },
            Response.ErrorListener { error ->
                complete(false)
                Log.d("ERROR", error.toString())
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        loginRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );

       queue.add(loginRequest)
    }

    fun verifyToken(context: Context, complete: (Boolean) -> Unit) {
        val queue = Volley.newRequestQueue(context)
        val urlBase = context.getString(R.string.be_url)
        val url = "${urlBase}/auth/verify"

        val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref.getString("jwt", "None")

        val jsonBody = JSONObject()
        jsonBody.put("token", token)

        val loginRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { _ ->
                complete(true)
            },
            Response.ErrorListener {  _->
                complete(false)
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        loginRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );

        queue.add(loginRequest)
    }
}