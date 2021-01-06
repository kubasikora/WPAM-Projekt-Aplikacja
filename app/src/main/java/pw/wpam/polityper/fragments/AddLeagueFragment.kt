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
                Toast.makeText(view.context,"You Selected ${sports.get(p2)}", Toast.LENGTH_SHORT).show()
            }

        }
        tournamentChoose.adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_dropdown_item_1line ,tournamentsNames)
        tournamentChoose.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                Log.d("INFO","Nothing")
                Toast.makeText(view.context,"There are no active leagues in this sport", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedTournament = tournamentObjectList.get(p2)
                Toast.makeText(view.context,"${tournamentObjectList.get(p2).name} with id ${tournamentObjectList.get(p2).id}", Toast.LENGTH_SHORT).show()
                Log.d("TournamentsNames", selectedTournament!!.name)
            }
        }

        view.createNewLeague.setOnClickListener{
            Toast.makeText(view.context,"Creating League in ${selectedTournament!!.name}", Toast.LENGTH_SHORT).show()
        }
        view.joinLeague.setOnClickListener{
            Toast.makeText(view.context,"Joining League", Toast.LENGTH_SHORT).show()
        }

        return view
    }

}