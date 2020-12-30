package pw.wpam.polityper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_available_leagues.*
import pw.wpam.polityper.adapters.ViewPageAdapter
import pw.wpam.polityper.fragments.LeaguesFragment
import pw.wpam.polityper.fragments.SettingsFragment

class AvailableLeaguesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_leagues)

        setUpTabs()
    }

    private fun setUpTabs(){
        val adapter = ViewPageAdapter(supportFragmentManager)
        adapter.addFragment(LeaguesFragment(),"Leagues")
        adapter.addFragment(SettingsFragment(), "settings")
        viewPagerLeagues.adapter = adapter
        tabsLeagues.setupWithViewPager(viewPagerLeagues)
        tabsLeagues.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_sports_24)
        tabsLeagues.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_settings_24)
    }

}