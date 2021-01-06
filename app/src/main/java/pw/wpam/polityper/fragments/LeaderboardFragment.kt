package pw.wpam.polityper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.LeaderboardRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration
import pw.wpam.polityper.models.Participant
import pw.wpam.polityper.services.LeaderboardService

class LeaderboardFragment(leagueId: Int?): Fragment() {
    private lateinit var leaderboardRecyclerAdapter: LeaderboardRecyclerAdapter
    private var leagueId = leagueId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        view.loadingSpinner.isVisible = true
        LeaderboardService.getLeaders(leagueId) { success, participants ->
            leaderboardRecyclerAdapter.update(participants)
            view.loadingSpinner.isVisible = false
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){
        val data = ArrayList<Participant>()
        leaderboardRecyclerAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.layoutManager = LinearLayoutManager(activity)
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        recycler_view.addItemDecoration(topSpacingDecorator)
        leaderboardRecyclerAdapter = LeaderboardRecyclerAdapter()
        recycler_view.adapter = leaderboardRecyclerAdapter
    }

}