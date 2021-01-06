package pw.wpam.polityper.adapters


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_bet.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.models.Bet
import pw.wpam.polityper.models.League
import pw.wpam.polityper.models.Participant
import pw.wpam.polityper.models.Tournament
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
                if (itemView.firstTeamPrediction.text.toString() == "" &&
                        itemView.secondTeamPrediction.text.toString() == ""){
                    Toast.makeText(itemView.context, "Put a number in prediction", Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.d("Info","proba")
                BetService.placeBet(betId, firstTeamPrediction.text.toString(), secondTeamPrediction.text.toString()) { success, newBet ->
                    Toast.makeText(itemView.context, "Bet Placed", Toast.LENGTH_SHORT).show()
                }
                }
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
                else {
                    firstTeamPrediction.setText("")
                    secondTeamPrediction.setText("")
                    firstTeamScore.setText(null)
                    secondTeamScore.setText(null)
                    placeBetButton.isClickable = true
                    firstTeamPrediction.isFocusable = true
                    secondTeamPrediction.isFocusable = true
                    backgroundColor.setColorFilter(Color.parseColor("#FFFFFFFF"), PorterDuff.Mode.SRC)
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
