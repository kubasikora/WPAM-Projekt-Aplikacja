package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.viewmodels.UserViewModel

class LoginFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        view.loginButton.setOnClickListener {
            val username = usernameLoginField.text.toString()
            val password = passwordLoginField.text.toString()
            userViewModel.login(username, password)
        }
        view.registerButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val navController: NavController = this.findNavController()

        userViewModel.currentUser.observe(this.viewLifecycleOwner,  Observer { user ->
            Log.d("LOGIN", "User has changed")
            if(user != null) {
                Log.d("LOGIN", "User not null")
                if(navController.currentDestination?.id == R.id.loginFragment) {
                    navController.navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }
        })
        userViewModel.loginSuccess.observe(this.viewLifecycleOwner, Observer { success ->
            if(success) {
                if(navController.currentDestination?.id == R.id.loginFragment) {
                    navController.navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            } else {
                view.findViewById<TextView>(R.id.passwordLoginField).text = ""
            }
        })

        userViewModel.loginFailedMessage.observe(this.viewLifecycleOwner, Observer { message ->
            view.findViewById<TextView>(R.id.loginErrorMessage).text = message
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController: NavController = this.findNavController()


    }
}