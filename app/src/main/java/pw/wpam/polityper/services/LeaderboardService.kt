package pw.wpam.polityper.services

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Leaderboard
import pw.wpam.polityper.models.Participant


object LeaderboardService {
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
    fun getLeaders(leagueId: Int?, complete: (Boolean, ArrayList<Participant>) -> Unit) {
        val urlBase = context?.getString(R.string.be_url)
        val url = "${urlBase}/api/betting/leagues/${leagueId}/leaders"

        val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")

        val participantRequest = object : JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    val leaders = gson.fromJson(response.toString(), Leaderboard::class.java)
                    Log.d("Size:", leaders.participants.size.toString())
                    complete(true, leaders.participants)
                },
                Response.ErrorListener { error ->
                    Log.d("ERROR", error.toString())
                }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        participantRequest.retryPolicy = this.retryPolicy

        this.queue?.add(participantRequest)
    }
}