package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_league.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Tournament
import pw.wpam.polityper.services.LeagueService

class AddLeagueFragment : Fragment() {

    lateinit var sportChoose: Spinner
    lateinit var tournamentChoose: Spinner
    val sports = arrayOf("FOOTBALL","TENNIS", "VOLLEYBALL")
    var tournamentObjectList : ArrayList<Tournament> = ArrayList()
    var tournamentsNames:Array<String>  =arrayOf()
    var selectedTournament: Tournament? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_add_league, container, false)
        view.joinLeague.isClickable=true
        view.createNewLeague.isClickable=true
        sportChoose = view.spinnerSport
        tournamentChoose = view.spinnerTournament


        val tournaments: MutableList<String> = ArrayList()

        sportChoose.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line ,sports)
        sportChoose.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                LeagueService.getTournamentsFromSport(sports.get(p2)){success,tournamentsList ->
                    tournamentObjectList = tournamentsList
                    tournaments.clear()
                    for (tournament in tournamentsList){
                    tournaments.add(tournament.name)
                    }
                    tournamentsNames = tournaments.toTypedArray()
                    Log.d("size",tournamentsNames.size.toString())
                    if(tournamentsNames.size==0){
                        selectedTournament = null
                        view.createNewLeague.isClickable = false
                        Toast.makeText(view.context,"There are no active leagues in this sport", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        view.createNewLeague.isClickable = true
                    }
                    tournamentChoose.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line ,tournamentsNames)
                }
            }

        }
        tournamentChoose.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line ,tournamentsNames)
        tournamentChoose.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedTournament = tournamentObjectList.get(p2)
            }
        }

        view.createNewLeague.setOnClickListener{
            if(view.newLeagueNameInput.text.toString()==""){
                Log.d("Denied:","No League name")
                Toast.makeText(view.context,"Enter league name", Toast.LENGTH_SHORT).show()
            }
            else{
                LeagueService.createNewLeague(view.newLeagueNameInput.text.toString(),selectedTournament!!){
                    success, newLeagueKey -> LeagueService.createNewParticipant(newLeagueKey) { success, successMessage ->
                    Toast.makeText(view.context, successMessage, Toast.LENGTH_SHORT).show()
                    }
                    val navController: NavController = view.findNavController()
                    navController.navigate(R.id.action_addLeagueFragment_to_dashboardFragment)
                }
            }

        }
        view.joinLeague.setOnClickListener{
            LeagueService.createNewParticipant(view.leagueKey.text.toString()){
                success, successMessage -> Toast.makeText(view.context,successMessage, Toast.LENGTH_SHORT).show()
            }
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_addLeagueFragment_to_dashboardFragment)
            view.joinLeague.isClickable=false
        }

        return view
    }

}