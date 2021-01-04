package pw.wpam.polityper.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pw.wpam.polityper.models.LeagueHeader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recycler_view_league.view.*
import pw.wpam.polityper.R
import kotlin.collections.ArrayList

class LeagueListRecyclerAdapter(var mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<LeagueHeader> = ArrayList()

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

    fun submitList(leagueList: List<LeagueHeader>){
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
                navController.navigate(R.id.action_dashboardFragment_to_leagueDetailFragment)
            }
        }

        fun bind(leagueHeader: LeagueHeader){
            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

            // set icon
            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(leagueHeader.tournamentIcon)
                    .into(leagueIcon)

            // set text fields
            leagueName.text = leagueHeader.leagueName
            tournamentName.text = leagueHeader.tournamentName
        }
    }
}
