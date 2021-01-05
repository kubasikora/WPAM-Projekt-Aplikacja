package pw.wpam.polityper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_settings.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.viewmodels.UserViewModel

class SettingsFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        view.logoutButton.setOnClickListener {
            userViewModel.logout()
        }
        userViewModel.currentUser.observe(this.viewLifecycleOwner, Observer { user ->
            view.findViewById<TextView>(R.id.usernameProfile).text = "Hey ${user?.username}!"
        })
        return view
    }
}
