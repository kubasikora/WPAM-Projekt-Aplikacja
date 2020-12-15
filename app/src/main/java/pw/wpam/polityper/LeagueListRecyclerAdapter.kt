package pw.wpam.polityper


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import pw.wpam.polityper.models.LeagueHeader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.recycler_view_item.view.*
import kotlin.collections.ArrayList


class LeagueListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var items: List<LeagueHeader> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LeagueViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
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

    class LeagueViewHolder
    constructor(
            itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val leagueIcon = itemView.icon
        val leagueName = itemView.leagueName
        val tournamentName = itemView.tournamentName

        init {
            itemView.setOnClickListener {
                val position:Int = adapterPosition
                Toast.makeText(itemView.context,"You clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(leagueHeader: LeagueHeader){

            val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(leagueHeader.tournamentIcon)
                    .into(leagueIcon)
            leagueName.setText(leagueHeader.leagueName)
            tournamentName.setText(leagueHeader.tournamentName)

        }

    }

}
