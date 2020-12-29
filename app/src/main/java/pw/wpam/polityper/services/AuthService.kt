package pw.wpam.polityper.services

import android.content.Context
import android.util.Log
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

object AuthService {

    fun loginUser(context: Context, username: String, password: String) {
        val queue = Volley.newRequestQueue(context)
        val urlBase = "https://wpamprojekt-dev.herokuapp.com"
        val url = "${urlBase}/auth/login/"

        val jsonBody = JSONObject()
        jsonBody.put("username", username)
        jsonBody.put("password", password)

        val loginRequest = object : JsonObjectRequest(Request.Method.POST, url, jsonBody,
            Response.Listener { response ->
                Log.d("INFO", response.toString())
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