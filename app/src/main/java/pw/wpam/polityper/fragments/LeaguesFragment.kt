package pw.wpam.polityper.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_available_leagues.*
import kotlinx.android.synthetic.main.fragment_leagues.*
import pw.wpam.polityper.AvailableLeaguesActivity
import pw.wpam.polityper.DataSource
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.LeagueListRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration

class LeaguesFragment : Fragment() {

    private lateinit var leagueListAdapter: LeagueListRecyclerAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bets_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }
    private fun addDataSet(){
        val data = DataSource.createDataSet()
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