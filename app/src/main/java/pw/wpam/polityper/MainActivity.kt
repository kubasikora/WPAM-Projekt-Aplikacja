package pw.wpam.polityper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.wpam.polityper.services.AuthService
import pw.wpam.polityper.services.BetService
import pw.wpam.polityper.services.LeaderboardService
import pw.wpam.polityper.services.LeagueService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContentView(R.layout.activity_main)
        AuthService.setContext(context)
        LeagueService.setContext(context)
        BetService.setContext(context)
        LeaderboardService.setContext(context)
    }
}