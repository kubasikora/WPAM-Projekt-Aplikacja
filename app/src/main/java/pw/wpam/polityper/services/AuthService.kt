package pw.wpam.polityper.services

import android.content.Context
import android.util.Log
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pw.wpam.polityper.models.LoginResponse

object AuthService {
    val gson = Gson()

    fun loginUser(context: Context, username: String, password: String) {
        val queue = Volley.newRequestQueue(context)
        val urlBase = "https://wpamprojekt-dev.herokuapp.com"
        val url = "${urlBase}/auth/login/"

        val jsonBody = JSONObject()
        jsonBody.put("username", username)
        jsonBody.put("password", password)

        val loginRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                val login = gson.fromJson(response.toString(), LoginResponse::class.java)
                Log.d("INFO", login.token)
                Log.d("INFO", login.user.id.toString())
            },
            Response.ErrorListener { error ->
                Log.d("ERROR", error.toString())
            }
        ) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

       queue.add(loginRequest)
    }
}