package pw.wpam.polityper.fragments

import pw.wpam.polityper.adapters.TeamFormRecyclerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_leaderboard.*
import kotlinx.android.synthetic.main.fragment_team_form.view.*
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.TopSpacingItemDecoration

class TeamFormFragment(form : ArrayList<String>) : Fragment() {
    private lateinit var teamFormRecyclerAdapter: TeamFormRecyclerAdapter
    private val teamForm = form


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_leaderboard, container, false)
        initRecyclerView(view)
        addDataSet()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamFormRecyclerAdapter.update(teamForm)
    }

    fun updateDataset(data: ArrayList<String>){
        teamFormRecyclerAdapter.update(data)
    }

    private fun addDataSet(){
        val data = ArrayList<String>()
        teamFormRecyclerAdapter.submitList(data)
    }

    private fun initRecyclerView(view: View){
        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        view.recycler_view.addItemDecoration(topSpacingDecorator)
        teamFormRecyclerAdapter = TeamFormRecyclerAdapter()
        view.recycler_view.adapter = teamFormRecyclerAdapter
    }
}