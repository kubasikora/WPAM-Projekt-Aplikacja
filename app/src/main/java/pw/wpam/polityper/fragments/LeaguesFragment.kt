package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_leagues.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.LeagueListRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration
import pw.wpam.polityper.models.League
import pw.wpam.polityper.models.Participant
import pw.wpam.polityper.services.UserLeaguesService

class LeaguesFragment : Fragment() {
    private lateinit var leagueListAdapter: LeagueListRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        UserLeaguesService.getUserLeagues() { success, participants ->
        leagueListAdapter.update(participants)
        }
        return inflater.inflate(R.layout.fragment_leagues, container, false)


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