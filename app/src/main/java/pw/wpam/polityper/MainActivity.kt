package pw.wpam.polityper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val restApiService = RestApiService("https://wpamprojekt-prod.herokuapp.com/api/","users")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            val stringLoad = restApiService.getJSONString("teams/1")
            val parser = Gson()
            val team = parser.fromJson(stringLoad, Team::class.java)
            Log.d("teamName", team.name)
            Log.d("teamId", team.id.toString())
            Log.d("HTTP_JSON", stringLoad)
            textField.post(Runnable { textField.setText(team.name) })
            Log.d("HTTP_JSON", "stringLoad")
        }
        job.start()
        if(job.isCompleted){
            job.cancel()
        }

    }
}