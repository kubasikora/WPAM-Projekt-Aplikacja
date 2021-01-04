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
import pw.wpam.polityper.R
import pw.wpam.polityper.viewmodels.UserViewModel

class SplashFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController: NavController = findNavController()
        userViewModel.currentUser.observe(this.viewLifecycleOwner, Observer { user ->
            Log.d("SPLASH", user.toString())
            if(user == null){
                navController.navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                navController.navigate(R.id.action_splashFragment_to_dashboardFragment)
            }
        })
    }
}