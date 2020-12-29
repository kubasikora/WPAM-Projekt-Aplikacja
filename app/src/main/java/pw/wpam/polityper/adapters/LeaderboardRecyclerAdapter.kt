package pw.wpam.polityper.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_leaders.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.LeaderHeader

class LeaderboardRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<LeaderHeader> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LeaderboardViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_leaders, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {

            is LeaderboardViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(leadersList: List<LeaderHeader>) {
        items = leadersList
    }

    class LeaderboardViewHolder
    constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val name  = itemView.username
        val score = itemView.score
        init {
            //set on click listiner
        }

        fun bind(leaderHeader: LeaderHeader) {
            name.setText(leaderHeader.username)
            score.setText(leaderHeader.score.toString())
        }

    }
}