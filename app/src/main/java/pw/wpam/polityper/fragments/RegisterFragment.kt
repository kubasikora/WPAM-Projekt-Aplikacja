package pw.wpam.polityper.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {
    private val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        val navController: NavController = findNavController()

        view.createAccountButton.setOnClickListener { _ ->
            val email = emailRegistrationField.text.toString()
            val username = usernameRegistrationField.text.toString()
            val password = passwordRegistrationField.text.toString()
            val password2 = password2RegistrationField.text.toString()
            registerViewModel.register(email, username, password, password2)
        }

        registerViewModel.registerSuccess.observe(this.viewLifecycleOwner, Observer { success ->
            if(success){
                navController.navigate(R.id.action_registerFragment_to_registrationConfirmFragment)
            }
        })

        registerViewModel.loading.observe(this.viewLifecycleOwner, Observer { loading ->
            view.loadingSpinner.isVisible = loading
        })

        registerViewModel.registerMessage.observe(this.viewLifecycleOwner, Observer { message ->
            registerErrorMessage.text = message
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        registerViewModel.reset()
    }


}