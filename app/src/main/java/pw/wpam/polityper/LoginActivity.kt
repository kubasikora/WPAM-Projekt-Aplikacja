package pw.wpam.polityper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_login.*
import pw.wpam.polityper.services.AuthService
import pw.wpam.polityper.services.ContestantService

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ContestantService.getAllContestants(this)

        val context = this

        loginButton.setOnClickListener{
            val username = context.findViewById<TextView>(R.id.username).text.toString()
            val password = context.findViewById<TextView>(R.id.password).text.toString()

            AuthService.loginUser(context, username, password) { success ->
                if(success){
                    Log.d("INFO", "Authorization successful")
                    val intent = Intent(this, AvailableLeaguesActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("ERROR", "Incorrect credentials")
                    context.findViewById<TextView>(R.id.password).text = ""
                }
            }

        }
    }
}