package pw.wpam.polityper


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_bet.view.*
import pw.wpam.polityper.models.GameBetHeader
import kotlin.collections.ArrayList


class BetsListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var items: List<GameBetHeader> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BetViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_bet, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {

            is BetViewHolder -> {
                holder.bind(items.get(position))
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(betsList: List<GameBetHeader>){
        items = betsList
    }

    class BetViewHolder
    constructor(
            itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val firstTeam = itemView.firstTeamName
        val secondTeam = itemView.secondTeamName

        init {
            itemView.betPlacer.setOnClickListener {
                val position:Int = adapterPosition
                Toast.makeText(itemView.context,"Bet Placed", Toast.LENGTH_SHORT).show()
            }
        }

        fun bind(betHeader: GameBetHeader){
            firstTeam.setText(betHeader.firstContestantName)
            secondTeam.setText(betHeader.secondContestantName)


        }

    }

}
