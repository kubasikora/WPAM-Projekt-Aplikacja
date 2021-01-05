package pw.wpam.polityper.services

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Contestant
import pw.wpam.polityper.models.Participant

object UserLeaguesService {
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
    fun getUserLeagues(complete: (Boolean, ArrayList<Participant>) -> Unit) {
        val urlBase = context?.getString(R.string.be_url)
        val url = "${urlBase}/api/betting/participants/mine"

        val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")

        val participantRequest = object : JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                var participants: MutableList<Participant> = ArrayList()
                for (i in 0 until response.length()) {
                    val participant = response.getJSONObject(i)
                    participants.add(gson.fromJson(participant.toString(), Participant::class.java))
                }
                Log.d("Size:", participants.size.toString())
                complete(true, participants as ArrayList<Participant>)
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