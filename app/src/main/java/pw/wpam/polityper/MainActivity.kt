package pw.wpam.polityper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import pw.wpam.polityper.services.AuthService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContentView(R.layout.activity_main)
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            //Use API Funcions here
        }
        job.start()
        if(job.isCompleted){
            job.cancel()
        }

        registerButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        logInButton.setOnClickListener{
            AuthService.verifyToken(context) { correct ->
                if(correct){
                    val intent = Intent(this, AvailableLeaguesActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

        }
    }
}