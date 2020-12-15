package pw.wpam.polityper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    loginButton.setOnClickListener{
        val intent = Intent(this, AvailableLeaguesActivity::class.java)
        startActivity(intent)
    }
}
}