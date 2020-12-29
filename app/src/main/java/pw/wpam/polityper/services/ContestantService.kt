package pw.wpam.polityper.services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pw.wpam.polityper.models.Contestant
import com.android.volley.DefaultRetryPolicy


object ContestantService {
    val gson = Gson()

    fun getAllContestants(context: Context) {
        val queue = Volley.newRequestQueue(context)
        val urlBase = "https://wpamprojekt-prod.herokuapp.com"
        val url = "${urlBase}/api/teams/contestants"

        val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref.getString("jwt", "None")
        if (token != null) {
            Log.d("AUTH", token)
        }

        val contestantRequest = object : JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                var contestants: MutableList<Contestant> = ArrayList()
                for (i in 0 until response.length()) {
                    val contestant = response.getJSONObject(i)
                    contestants.add(gson.fromJson(contestant.toString(), Contestant::class.java))
                }

                for (contestant in contestants) {
                    Log.d("INFO", contestant.name)
                }
            },
            Response.ErrorListener { error ->
                Log.d("ERROR", error.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                headers["Authorization"]?.let { Log.d("HEADER", it) }
                return headers
            }
        }

        contestantRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );

        queue.add(contestantRequest)
    }
}