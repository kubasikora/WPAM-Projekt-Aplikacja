package pw.wpam.polityper.adapters


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_bet.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Bet
import pw.wpam.polityper.services.BetService
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
        val firstTeamScore = itemView.firstTeamScore
        val secondTeamScore = itemView.secondTeamScore
        val firstTeamPrediction = itemView.firstTeamPrediction
        val secondTeamPrediction = itemView.secondTeamPrediction
        var backgroundColor = itemView.dateOfGame.background
        val dateTextView = itemView.dateOfGame
        val placeBetButton = itemView.betPlacer
        init {
            itemView.betPlacer.setOnClickListener {
                val position = adapterPosition
                val betId = items[position].id
                BetService.placeBet(betId, firstTeamPrediction.text.toString(), secondTeamPrediction.text.toString()){
                    success, newBet->
                    Toast.makeText(itemView.context,"Bet Placed", Toast.LENGTH_SHORT).show()
                }
            }
            itemView.chujowyGuziczek.setOnClickListener { view ->
                val position = adapterPosition
                val matchId = items[position].match.id
                val navController: NavController = view.findNavController()

                val bundle = Bundle()
                bundle.putInt("matchId", matchId)

                navController.navigate(R.id.action_leagueDetailFragment_to_matchDetailFragment, bundle)
            }
        }

        fun bind(bet: Bet){
            firstTeam.setText(bet.match.playerOne.name)
            secondTeam.setText(bet.match.playerTwo.name)
            colorBets(bet)
            createDate(bet)
        }

        fun colorBets(bet: Bet){
            if(bet.match.finished) {
                firstTeamScore.setText(bet.match.playerOneResult.toString())
                secondTeamScore.setText(bet.match.playerTwoResult.toString())
                firstTeamPrediction.setText(bet.playerOnePrediction.toString())
                secondTeamPrediction.setText(bet.playerTwoPrediction.toString())
                placeBetButton.isClickable=false
                firstTeamPrediction.isFocusable = false
                secondTeamPrediction.isFocusable = false
                if(bet.valid==false){
                    backgroundColor.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC)
                }
                else if (bet.match.playerOneResult == bet.playerOnePrediction &&
                        bet.match.playerTwoResult == bet.playerTwoPrediction){
                    backgroundColor.setColorFilter(Color.parseColor("#A000FF00"), PorterDuff.Mode.SRC)
                }
                else if(bet.match.playerOneResult > bet.match.playerTwoResult &&
                        bet.playerOnePrediction > bet.playerTwoPrediction){
                    backgroundColor.setColorFilter(Color.parseColor("#A0FFFF00"), PorterDuff.Mode.SRC)
                }
                else if(bet.match.playerOneResult < bet.match.playerTwoResult &&
                        bet.playerOnePrediction < bet.playerTwoPrediction){
                    backgroundColor.setColorFilter(Color.parseColor("#A0FFFF00"), PorterDuff.Mode.SRC)
                }
                else if(bet.match.playerOneResult == bet.match.playerTwoResult &&
                        bet.playerOnePrediction == bet.playerTwoPrediction) {
                    backgroundColor.setColorFilter(Color.parseColor("#A0FFFF00"), PorterDuff.Mode.SRC)
                }
                else{
                    backgroundColor.setColorFilter(Color.parseColor("#A0FF0000"), PorterDuff.Mode.SRC)
                }

            } else{
                if(bet.valid) {
                    firstTeamPrediction.setText(bet.playerOnePrediction.toString())
                    secondTeamPrediction.setText(bet.playerTwoPrediction.toString())
                }
            }
        }
        @SuppressLint("NewApi")
        fun createDate(bet: Bet){
            val dateOfStart = LocalDate.parse(bet.match.dateOfStart, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            val timeOfStart = LocalTime.parse(bet.match.dateOfStart, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            val day = String.format("%02d",dateOfStart.dayOfMonth)
            val month = String.format("%02d",dateOfStart.monthValue)
            val year = String.format("%02d",dateOfStart.year)
            val ddmmyyyy = "${day}-${month}-${year}"
            val hours = String.format("%02d",timeOfStart.hour)
            val minutes = String.format("%02d",timeOfStart.minute)
            val hhss = ", ${hours}:${minutes}"
            val date = ddmmyyyy + hhss
            dateTextView.setText(date)
        }

    }

}
