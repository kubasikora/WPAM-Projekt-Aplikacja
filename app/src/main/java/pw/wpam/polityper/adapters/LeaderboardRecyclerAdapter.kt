package pw.wpam.polityper.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_leaders.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Participant

class LeaderboardRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Participant> = ArrayList()

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
    fun update(participantList: List<Participant>){
        val data = ArrayList<Participant>()
        for (participant in participantList) {
            data.add(participant)
            Log.d("INFO", participant.toString())
        }
        items = data
        this.notifyDataSetChanged()
    }

    fun submitList(participantsList: List<Participant>) {
        items = participantsList
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

        fun bind(participant: Participant) {
            name.setText(participant.user.username)
            score.setText(participant.points.toString())
        }

    }
}