package pw.wpam.polityper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pw.wpam.polityper.services.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize services
        val context = this
        AuthService.setContext(context)
        LeagueService.setContext(context)
        BetService.setContext(context)
        LeaderboardService.setContext(context)
        NewsService.setContext(context)
    }
}