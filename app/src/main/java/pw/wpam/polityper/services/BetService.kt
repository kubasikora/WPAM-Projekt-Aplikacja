package pw.wpam.polityper.services

import android.content.Context
import android.content.LocusId
import android.gesture.Prediction
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import pw.wpam.polityper.R
import pw.wpam.polityper.models.*
import java.nio.charset.StandardCharsets

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
        val sharedPref = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")
        val betRequest = object : JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    var bets: MutableList<Bet> = ArrayList()
                    for (i in 0 until response.length()) {
                        val bet = response.getJSONObject(i)
                        bets.add(gson.fromJson(bet.toString(), Bet::class.java))
                    }
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

    fun placeBet(betId: Int?, playerOnePrediction: String, playerTwoPrediction: String,
                complete: (Boolean, Bet?) -> Unit){
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/api/betting/bets/${betId}/place"

        val jsonBody = JSONObject()
        jsonBody.put("valid", true)
        jsonBody.put("playerOnePrediction", playerOnePrediction.toInt())
        jsonBody.put("playerTwoPrediction", playerTwoPrediction.toInt())
        val sharedPref = BetService.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")

        val placeBetRequest = object: JsonObjectRequest(Request.Method.PATCH, url, jsonBody,
                                                       Response.Listener { response ->
            complete(true, null)
        },
        Response.ErrorListener { error ->
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

        placeBetRequest.retryPolicy = BetService.retryPolicy
        this.queue?.add(placeBetRequest)
    }
}
