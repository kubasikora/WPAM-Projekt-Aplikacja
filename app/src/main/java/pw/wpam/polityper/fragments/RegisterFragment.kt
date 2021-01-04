package pw.wpam.polityper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.fragment_register.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.viewmodels.RegisterViewModel

class RegisterFragment : Fragment() {
    private val registerViewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        view.createAccountButton.setOnClickListener { view ->
            val email = view.findViewById<TextView>(R.id.email).text.toString()
            val username = view.findViewById<TextView>(R.id.username).text.toString()
            val password = view.findViewById<TextView>(R.id.password).text.toString()
            val password2 = view.findViewById<TextView>(R.id.password2).text.toString()
        }

        return view
    }


}