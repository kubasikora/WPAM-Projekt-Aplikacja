package pw.wpam.polityper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.wpam.polityper.services.AuthService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContentView(R.layout.activity_main)
        AuthService.setContext(context)
    }
}