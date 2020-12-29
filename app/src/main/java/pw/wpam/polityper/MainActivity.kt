package pw.wpam.polityper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import pw.wpam.polityper.services.AuthService
import pw.wpam.polityper.services.ContestantService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AuthService.loginUser(this,"admin", "admin")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            //Use API Funcions here
        }
        job.start()
        if(job.isCompleted){
            job.cancel()
        }

        registerButtor.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            }
        logInButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}