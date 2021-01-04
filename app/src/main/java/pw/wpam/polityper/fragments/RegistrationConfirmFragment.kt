package pw.wpam.polityper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_registration_confirm.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.viewmodels.RegisterViewModel

class RegistrationConfirmFragment : Fragment() {
    private val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_registration_confirm, container, false)
        val navController: NavController = findNavController()
        view.proceedButton.setOnClickListener { _ ->
            navController.navigate(R.id.action_registrationConfirmFragment_to_loginFragment)
        }
        registerViewModel.registerMessage.observe(this.viewLifecycleOwner, Observer { message ->
            view.findViewById<TextView>(R.id.usernameField).text = message
        })
        return view
    }
}