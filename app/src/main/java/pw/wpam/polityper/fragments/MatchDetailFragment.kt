package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_match_detail.*
import kotlinx.android.synthetic.main.fragment_match_detail.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.ViewPageAdapter
import pw.wpam.polityper.services.BetService
import pw.wpam.polityper.models.MatchStats

class MatchDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_match_detail, container, false)
        val matchId = this.arguments?.getInt("matchId")

        val playerOnePagerAdapter = ViewPageAdapter(this.requireActivity())
        view.viewPagerPlayerOneForm.adapter = playerOnePagerAdapter
        playerOnePagerAdapter.addFragment(TeamFormFragment(ArrayList<String>()), "playerOne")

        val playerTwoPagerAdapter = ViewPageAdapter(this.requireActivity())
        view.viewPagerPlayerTwoForm.adapter = playerTwoPagerAdapter
        playerTwoPagerAdapter.addFragment(TeamFormFragment(ArrayList<String>()), "playerTwo")

        view.loadingSpinner.isVisible = true
        BetService.getMatchStatistics(matchId!!) { success, stats: MatchStats? ->
            view.loadingSpinner.isVisible = false
            view.findViewById<TextView>(R.id.statsPlayerOne).text = stats?.playerOne?.name
            view.findViewById<TextView>(R.id.statsPlayerTwo).text = stats?.playerTwo?.name
            playerOnePagerAdapter.update(stats?.playerOneForm!!)
            playerTwoPagerAdapter.update(stats?.playerTwoForm!!)
        }
        return view
    }
}