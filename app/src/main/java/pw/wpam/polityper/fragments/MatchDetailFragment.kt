package pw.wpam.polityper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import pw.wpam.polityper.R
import pw.wpam.polityper.services.BetService
import pw.wpam.polityper.models.MatchStats

class MatchDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_match_detail, container, false)
        val matchId = this.arguments?.getInt("matchId")
        view.findViewById<TextView>(R.id.matchDetailExampleString).text = matchId.toString()
        BetService.getMatchStatistics(matchId!!) { success, stats: MatchStats? ->
            view.findViewById<TextView>(R.id.matchDetailExampleString).text = stats.toString()
        }
        return view
    }
}