package pw.wpam.polityper

import android.net.Uri
import android.util.Log
import com.google.gson.Gson

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class RestApiService constructor(address :String?, apiK: String?) {
    val apiAddress = address
    val API_KEY = apiK


    fun getUrlBytes(urlSpec: String): ByteArray{
        val url= URL(urlSpec)
        val connection = url.openConnection() as HttpsURLConnection
        try {
            val out = ByteArrayOutputStream()
            val input = connection.inputStream

            if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException(connection.responseMessage)
            }

            var bytesRead: Int
            val buffer = ByteArray(1240)
            do {
                bytesRead = input.read(buffer)
                out.write(buffer, 0, bytesRead)
            } while (input.read(buffer) > 0)
            out.close()

            return out.toByteArray()
        }
        catch (e: IOException){
            Log.e("ERROR_HTTP_CON", e.message as String)
            return ByteArray(0)

        }finally {
            connection.disconnect()
        }
    }

    fun getUrlString(urlSpec: String): String{
        return String(getUrlBytes(urlSpec))
    }

    fun getJSONString(resource: String): String{
        var jsonString = "NONE"
        try{
            val url = Uri.parse(apiAddress+ resource)
                .buildUpon()
                //.appendQueryParameter("method","GET")
                //.appendQueryParameter("format", "JSON")
                //.appendQueryParameter("nojsoncallback","1")
                .build().toString()
            jsonString  = getUrlString(url)
        }catch (je:JSONException){
            Log.e("JSON error", je.message as String)
        }
        return jsonString
    }



}