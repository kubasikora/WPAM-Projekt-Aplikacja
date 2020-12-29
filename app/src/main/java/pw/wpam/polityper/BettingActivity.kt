package pw.wpam.polityper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_betting.*
import pw.wpam.polityper.adapters.BetsListRecyclerAdapter
import pw.wpam.polityper.adapters.ViewPageAdapter
import pw.wpam.polityper.fragments.BetsFragment
import pw.wpam.polityper.fragments.LeadersFragment

class BettingActivity : AppCompatActivity() {

    private lateinit var betListAdapter: BetsListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_betting)

        setUpTabs()
    }

    private fun setUpTabs(){
        val adapter = ViewPageAdapter(supportFragmentManager)
        adapter.addFragment(BetsFragment(),"Betting")
        adapter.addFragment(LeadersFragment(), "Leaderboard")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_sports_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_group_24)
    }

}