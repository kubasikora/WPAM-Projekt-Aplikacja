package pw.wpam.polityper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_league_bets.*
import kotlinx.android.synthetic.main.fragment_leagues.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.BetsListRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration
import pw.wpam.polityper.models.Bet
import pw.wpam.polityper.services.BetService


class LeagueBetsFragment(leagueId: Int?) : Fragment(){
    private lateinit var betListAdapter: BetsListRecyclerAdapter
    private var leagueId = leagueId

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_league_bets, container, false)
        view.loadingSpinner.isVisible = true
        BetService.getBetsInLeague(leagueId) { success, participants ->
            betListAdapter.update(participants)
            view.loadingSpinner.isVisible=false
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){
        val data = ArrayList<Bet>()
        betListAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.layoutManager = LinearLayoutManager(activity)
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        recycler_view.addItemDecoration(topSpacingDecorator)
        betListAdapter = BetsListRecyclerAdapter()
        recycler_view.adapter = betListAdapter
    }
}