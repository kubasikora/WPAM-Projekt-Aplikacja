package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_league_detail.*
import kotlinx.android.synthetic.main.fragment_league_detail.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.ViewPageAdapter
import pw.wpam.polityper.services.LeagueService

class LeagueDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_league_detail, container, false)
        Log.d("LigaId", this.arguments?.getInt("leagueId").toString())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }

    private fun setUpTabs(){
        val stringWithLeagueKey = "League key: ${this.arguments?.getString("leagueKey")}"
        this.view?.textView3?.setText(stringWithLeagueKey)
        val pagerAdapter = ViewPageAdapter(this.requireActivity())
        pagerAdapter.addFragment(LeagueBetsFragment(this.arguments?.getInt("leagueId")), "Betting")
        pagerAdapter.addFragment(LeaderboardFragment(this.arguments?.getInt("leagueId")), "Leaderboard")
        this.viewPagerDetail.adapter = pagerAdapter
        val tabTitles: Array<String> = arrayOf("Betting", "Leaderboard")
        val tabIcons: Array<Int> = arrayOf(R.drawable.ic_baseline_sports_24, R.drawable.ic_baseline_group_24)

        TabLayoutMediator(this.tabsDetail, this.viewPagerDetail) { tab, position ->
            tab.text = tabTitles[position]
            tab.setIcon(tabIcons[position])
            this.viewPagerDetail.setCurrentItem(tab.position, true)
        }.attach()
    }
}