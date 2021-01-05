package pw.wpam.polityper.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_settings.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.services.AuthService
import pw.wpam.polityper.viewmodels.UserViewModel

class SettingsFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        view.logoutButton.setOnClickListener {
            userViewModel.logout()
        }
        view.saveNewProfile.setOnClickListener {
            val firstName = view.findViewById<TextView>(R.id.firstNameInput).text.toString()
            val lastName = view.findViewById<TextView>(R.id.lastNameInput).text.toString()
            userViewModel.updateUserProfile(firstName, lastName)
        }
        view.saveNewPassword.setOnClickListener {
            val password1 = view.findViewById<TextView>(R.id.newPassword).text.toString()
            val password2 = view.findViewById<TextView>(R.id.newPasswordConfirm).text.toString()
            if(password1 != password2){
                view.findViewById<TextView>(R.id.newPassword).text = ""
                view.findViewById<TextView>(R.id.newPasswordConfirm).text = ""
                Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_LONG)
                    .show()
            }
            AuthService.changePassword(password1, password2) { success ->
                if(success){
                    Toast.makeText(activity, "Password updated successfully", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(activity, "Error occurred", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        userViewModel.updatingProfile.observe(this.viewLifecycleOwner, Observer { p ->
            val isUpdating = p.first
            val success = p.second
            if(!isUpdating) {
                if (success) {
                    Toast.makeText(activity, "Profile updated successfully", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
        userViewModel.currentUser.observe(this.viewLifecycleOwner, Observer { user ->
            view.findViewById<TextView>(R.id.usernameProfile).text = "Hey ${user?.username}!"
            view.findViewById<TextView>(R.id.firstNameInput).text = user?.firstName
            view.findViewById<TextView>(R.id.lastNameInput).text = user?.lastName
        })
        return view
    }
}
