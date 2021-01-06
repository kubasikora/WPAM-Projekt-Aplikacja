package pw.wpam.polityper.services

import android.content.Context
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
import pw.wpam.polityper.models.League
import pw.wpam.polityper.models.LeagueShort
import pw.wpam.polityper.models.Participant
import pw.wpam.polityper.models.Tournament

object LeagueService {
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
    fun getUserParticipants(complete: (Boolean, ArrayList<Participant>) -> Unit) {
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

    fun createNewLeague(name: String, tournament: Tournament,
                        complete: (Boolean, String) -> Unit){
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/api/betting/post_league"

        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("leagueKey","someRandKey")
        jsonBody.put("tournament", tournament.id)
        val sharedPref = LeagueService.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")

        val createLeague = object: JsonObjectRequest(Request.Method.POST, url, jsonBody,
                Response.Listener { response ->

                    val leagueKey = LeagueService.gson.fromJson(response.toString(), LeagueShort::class.java).leagueKey
                    complete(true, leagueKey)
                },
                Response.ErrorListener { error ->
                    complete(false, "Something went wrong")
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

        createLeague.retryPolicy = LeagueService.retryPolicy
        this.queue?.add(createLeague)
    }


    fun getTournamentsFromSport(sport: String,
    complete: (Boolean, ArrayList<Tournament>) -> Unit){
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/api/teams/tournaments/active?sport=${sport}"


        val sharedPref = LeagueService.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")

        val tournamentsRequest = object: JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    var tournaments: MutableList<Tournament> = ArrayList()
                    for (i in 0 until response.length()) {
                        val tournament = response.getJSONObject(i)
                        tournaments.add(BetService.gson.fromJson(tournament.toString(), Tournament::class.java))
                    }
                    complete(true, tournaments as ArrayList<Tournament>)
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

        tournamentsRequest.retryPolicy = this.retryPolicy

        this.queue?.add(tournamentsRequest)
    }

    fun createNewParticipant(leagueKey: String, complete: (Boolean, String) -> Unit){
        val urlBase: String? = this.context?.getString(R.string.be_url)
        val url = "${urlBase}/api/betting/league/${leagueKey}/join"

        val jsonBody = JSONObject()
        jsonBody.put("points", 0)

        val sharedPref = LeagueService.context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPref?.getString("jwt", "None")

        val createParticipant = object: JsonObjectRequest(Request.Method.POST, url, jsonBody,
                Response.Listener { response ->
                    complete(true, "Joined")
                },
                Response.ErrorListener { error ->
                    complete(false, "Wrong league key")
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

        createParticipant.retryPolicy = LeagueService.retryPolicy
        this.queue?.add(createParticipant)
    }

}