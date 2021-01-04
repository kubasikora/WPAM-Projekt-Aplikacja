package pw.wpam.polityper.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.leaders_fragment.*
import pw.wpam.polityper.DataSourceLeaderboard
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.LeaderboardRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration

class LeadersFragment: Fragment() {

    private lateinit var leaderboardRecyclerAdapter: LeaderboardRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.leaders_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }
    private fun addDataSet(){
        val data = DataSourceLeaderboard.createDataSet()
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