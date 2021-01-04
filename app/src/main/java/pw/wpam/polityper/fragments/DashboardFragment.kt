package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_available_leagues.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.ViewPageAdapter

import pw.wpam.polityper.viewmodels.UserViewModel

class DashboardFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val navController: NavController = findNavController()
        userViewModel.currentUser.observe(this.viewLifecycleOwner, Observer { user ->
            Log.d("DASHBOARD", "user has changed")
            if(user == null){
                Log.d("DASHBOARD", "user logged out, going to login")
                navController.navigate(R.id.action_dashboardFragment_to_loginFragment)
            }
        })
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }

    private fun setUpTabs(){
        val pagerAdapter = ViewPageAdapter(this.requireActivity())
        pagerAdapter.addFragment(LeaguesFragment(), "Leagues")
        pagerAdapter.addFragment(SettingsFragment(), "Settings")
        this.viewPagerLeagues.adapter = pagerAdapter

        this.tabsLeagues.getTabAt(0)?.setIcon(R.drawable.ic_baseline_sports_24)
        this.tabsLeagues.getTabAt(1)?.setIcon(R.drawable.ic_baseline_settings_24)
        TabLayoutMediator(this.tabsLeagues, this.viewPagerLeagues) { _, _ -> }.attach()
    }

}