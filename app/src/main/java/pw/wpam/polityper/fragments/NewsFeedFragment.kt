package pw.wpam.polityper.fragments

import pw.wpam.polityper.adapters.TeamFormRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_leaderboard.view.*
import kotlinx.android.synthetic.main.fragment_team_form.view.recycler_view
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.NewsFeedRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration
import pw.wpam.polityper.models.Article

class NewsFeedFragment(form : ArrayList<Article>) : Fragment() {
    private lateinit var newsFeedRecyclerAdapter: NewsFeedRecyclerAdapter
    private val teamForm = form


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_news_feed, container, false)
        initRecyclerView(view)
        addDataSet()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsFeedRecyclerAdapter.update(teamForm)
    }

    fun updateDataset(data: ArrayList<Article>){
        newsFeedRecyclerAdapter.update(data)
    }

    private fun addDataSet(){
        val data = ArrayList<Article>()
        newsFeedRecyclerAdapter.submitList(data)
    }

    private fun initRecyclerView(view: View){
        view.recycler_view.layoutManager = LinearLayoutManager(activity)
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        view.recycler_view.addItemDecoration(topSpacingDecorator)
        newsFeedRecyclerAdapter = NewsFeedRecyclerAdapter(requireContext())
        view.recycler_view.adapter = newsFeedRecyclerAdapter
    }
}