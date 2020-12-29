package pw.wpam.polityper.services

import android.content.Context
import android.util.Log
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pw.wpam.polityper.models.Contestant


object ContestantService {
    val gson = Gson()

    fun getAllContestants(context: Context) {
        val queue = Volley.newRequestQueue(context)
        val urlBase = "https://wpamprojekt-dev.herokuapp.com"
        val url = "${urlBase}/api/teams/contestants"

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
        ) { }

       queue.add(contestantRequest)
    }
}