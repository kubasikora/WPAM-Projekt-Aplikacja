package pw.wpam.polityper

import android.net.Uri
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import pw.wpam.polityper.models.LeagueDataClass
import pw.wpam.polityper.models.TeamDataClass
import pw.wpam.polityper.models.TeamSnippetDataClass
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

    fun getTeamSnippetsList() : List<TeamSnippetDataClass>{
        val stringLoad = this.getJSONString("teams/contestants")
        val parser = Gson()
        val listType = object : TypeToken<List<TeamSnippetDataClass>>() {}.type
        val teamDataClassList: List<TeamSnippetDataClass> = parser.fromJson(stringLoad, listType)
        return teamDataClassList
    }

    fun getTeam(teamId: Int) : TeamDataClass {
        val stringLoad = this.getJSONString("teams/"+teamId.toString())
        val parser = Gson()
        val teamObject = parser.fromJson(stringLoad, TeamDataClass::class.java)
        return teamObject
    }

    fun getLeague(leagueId: Int) : LeagueDataClass {
        val stringLoad = this.getJSONString("betting/leagues/"+leagueId.toString())
        val parser = Gson()
        val leagueObject = parser.fromJson(stringLoad, LeagueDataClass::class.java)
        return leagueObject
    }
}