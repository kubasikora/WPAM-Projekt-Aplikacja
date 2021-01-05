package pw.wpam.polityper.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_bet.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Bet
import pw.wpam.polityper.models.League
import pw.wpam.polityper.models.Participant
import pw.wpam.polityper.models.Tournament
import pw.wpam.polityper.services.BetService
import kotlin.collections.ArrayList


class BetsListRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private var items: List<Bet> = ArrayList()

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
    fun update(betsList: List<Bet>){
        val data = ArrayList<Bet>()
        for (bet in betsList) {
            data.add(bet)
            Log.d("INFO", bet.match.playerOne.name)
        }
        items = data
        this.notifyDataSetChanged()
    }
    fun submitList(betsList: List<Bet>){
        items = betsList
    }

    inner class BetViewHolder
    constructor(
            itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val firstTeam = itemView.firstTeamName
        val secondTeam = itemView.secondTeamName
        val firstTeamscore = itemView.firstTeamPrediction
        val secondTeamScore = itemView.secondTeamPrediction

        init {
            itemView.betPlacer.setOnClickListener {
                val position = adapterPosition
                val betId = items[position].id
                BetService.placeBet(betId, firstTeamscore.text.toString(), secondTeamScore.text.toString()){success, newBet->
                    Toast.makeText(itemView.context,"Bet Placed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun bind(bet: Bet){
            firstTeam.setText(bet.match.playerOne.name)
            secondTeam.setText(bet.match.playerTwo.name)
            if (bet.valid){
                if(bet.match.finished) {
                    firstTeamscore.setText(bet.match.playerOneResult.toString())
                    firstTeamscore.isFocusable = false
                    secondTeamScore.setText(bet.match.playerTwoResult.toString())
                    secondTeamScore.isFocusable = false
                }
                else{
                    firstTeamscore.setText(bet.playerOnePrediction.toString())
                    secondTeamScore.setText(bet.playerTwoPrediction.toString())
                }
            }
        }

    }

}
