package pw.wpam.polityper.adapters

import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.PorterDuff
import android.util.Log
import kotlinx.android.synthetic.main.recycler_view_form.view.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pw.wpam.polityper.R

class TeamFormRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FormViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_form, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FormViewHolder -> {
                holder.bind(items[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(teamFormList: ArrayList<String>){
        val data = ArrayList<String>()
        for (teamForm in teamFormList) {
            data.add(teamForm)
        }
        items = data
        this.notifyDataSetChanged()
    }

    fun submitList(formList: ArrayList<String>) {
        items = formList
    }

    class FormViewHolder
    constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(teamForm: String) {
            itemView.formText.text = teamForm
            Log.d("chuu", teamForm[0].toString())
            if(teamForm[0] == 'W'){
                itemView.formText.background.setColorFilter(Color.parseColor("#8000FF00"), PorterDuff.Mode.SRC)
            }
            if(teamForm[0] == 'D'){
                itemView.formText.background.setColorFilter(Color.parseColor("#80FFFF00"), PorterDuff.Mode.SRC)
            }
            if(teamForm[0] == 'L'){
                itemView.formText.background.setColorFilter(Color.parseColor("#80FF0000"), PorterDuff.Mode.SRC)
            }
        }
    }
}