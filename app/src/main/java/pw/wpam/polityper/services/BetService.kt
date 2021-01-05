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
import pw.wpam.polityper.models.Bet

object BetService {
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
    fun getBetsInLeague(leagueId: Int?, complete: (Boolean, ArrayList<Bet>) -> Unit) {
        val urlBase = context?.getString(R.string.be_url)
        val url = "${urlBase}/api/betting/bets/mine/inLeague?league=${leagueId}"
        Log.d("URL:", url)
        val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")

        val betRequest = object : JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    var bets: MutableList<Bet> = ArrayList()
                    for (i in 0 until response.length()) {
                        val bet = response.getJSONObject(i)
                        Log.d("Bet:",bet.toString())
                        bets.add(gson.fromJson(bet.toString(), Bet::class.java))
                    }
                    Log.d("Size:", bets.size.toString())
                    complete(true, bets as ArrayList<Bet>)
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

        betRequest.retryPolicy = this.retryPolicy

        this.queue?.add(betRequest)
    }
}