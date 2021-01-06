package pw.wpam.polityper.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pw.wpam.polityper.models.League
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recycler_view_league.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Participant
import pw.wpam.polityper.models.Tournament
import pw.wpam.polityper.services.LeagueService
import kotlin.collections.ArrayList

class LeagueListRecyclerAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<League> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LeagueViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_league, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is LeagueViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(participantList: List<Participant>){
        val data = ArrayList<League>()
        for (participant in participantList) {
            data.add(participant.league)
            Log.d("INFO", participant.league.name)
        }
        items = data
        this.notifyDataSetChanged()
    }

    fun submitList(leagueList: List<League>){
        items = leagueList
    }

    inner class LeagueViewHolder
    constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val leagueIcon = itemView.icon
        val leagueName = itemView.leagueName
        val tournamentName = itemView.tournamentName

        init {
            itemView.setOnClickListener {view ->
                val navController: NavController = view.findNavController()
                val position = adapterPosition
                val leagueId = items[position].id
                val leagueKey = items[position].leagueKey
                val bundle = Bundle()
                bundle.putInt("leagueId", leagueId)
                bundle.putString("leagueKey", leagueKey)
                 navController.navigate(R.id.action_dashboardFragment_to_leagueDetailFragment,bundle)
            }
        }

        fun bind(league: League){
            val leagueImage: Int
            if(league.tournament.sport=="FOOTBALL") {
                leagueImage = R.drawable.ic_baseline_sports_soccer_24
            }
            else if(league.tournament.sport=="TENNIS") {
                leagueImage = R.drawable.ic_baseline_sports_tennis_24
            }
            else if(league.tournament.sport=="VOLLEYBALL") {
                leagueImage = R.drawable.ic_baseline_sports_volleyball_24
            }
            else{
                leagueImage = R.drawable.ic_baseline_add_circle_24
            }
            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

            // set icon
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(leagueImage)
                    .into(leagueIcon)

            // set text fields
            leagueName.text = league.name
            tournamentName.text = league.tournament.name
        }
    }
}
