package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_leagues.*
import kotlinx.android.synthetic.main.fragment_leagues.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.LeagueListRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration
import pw.wpam.polityper.models.League
import pw.wpam.polityper.services.LeagueService

class LeaguesFragment : Fragment() {
    private lateinit var leagueListAdapter: LeagueListRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_leagues, container, false)
        view.loadingSpinner.isVisible = true
        LeagueService.getUserParticipants { success, participants ->
            leagueListAdapter.update(participants)
            view.loadingSpinner.isVisible= false
        }


        view.newLeagueButton.setOnClickListener {
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_dashboardFragment_to_addLeagueFragment)
            Log.d("INFO:","CLICK")
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        LeagueService.getUserParticipants { success, participants ->
            leagueListAdapter.update(participants)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){
        val data = ArrayList<League>()
        leagueListAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            leagueListAdapter = LeagueListRecyclerAdapter(requireContext())
            adapter = leagueListAdapter
        }
    }

}