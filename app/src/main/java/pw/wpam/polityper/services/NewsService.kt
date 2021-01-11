package pw.wpam.polityper.services

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pw.wpam.polityper.R
import pw.wpam.polityper.models.News
import java.nio.charset.StandardCharsets

object NewsService {
    val gson = Gson()
    private var context: Context? = null
    private var queue: RequestQueue? = null
    private var apiKey: String? = null
    private val retryPolicy = DefaultRetryPolicy(
        60000,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    )

    fun setContext(context: Context){
        this.context = context
        this.queue = Volley.newRequestQueue(this.context)

        val service = this
        context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA).apply {
            service.apiKey = metaData.getString("org.newsapi.API_KEY")
        }
    }

    fun getNews(query: String, complete: (Boolean, News) -> Unit) {
        val urlBase = context?.getString(R.string.news_url)
        val url = "${urlBase}?q=${query}"

        val newsRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val news = NewsService.gson.fromJson(response.toString(), News::class.java)
                complete(true, news)
            },
            Response.ErrorListener { error ->
                Log.d("ERROR", error.toString())
                Log.d("ERROR", error.networkResponse.data.toString(StandardCharsets.UTF_8))
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Api-Key"] = apiKey.toString()
                headers["user-agent"] = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
                return headers
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        newsRequest.retryPolicy = this.retryPolicy
        this.queue?.add(newsRequest)
    }
}
