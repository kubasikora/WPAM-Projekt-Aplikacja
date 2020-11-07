package pw.wpam.polityper
import kotlinx.coroutines.*

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val restApiService = RestApiService("https://wpamprojekt-prod.herokuapp.com/api/","users")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val job: Job = CoroutineScope(Dispatchers.IO).launch {
            Log.d("HTTP_JSON", restApiService.getJSONString("teams"))
        }

        job.start()
        if(job.isCompleted){
            job.cancel()
        }
    }
}