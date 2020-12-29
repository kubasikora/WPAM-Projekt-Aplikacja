package pw.wpam.polityper.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_available_leagues.*
import pw.wpam.polityper.DataSourceBetting
import pw.wpam.polityper.R
import pw.wpam.polityper.adapters.BetsListRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration

class BetsFragment : Fragment() {

    private lateinit var betListAdapter: BetsListRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bets_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        addDataSet()
    }
    private fun addDataSet(){
        val data = DataSourceBetting.createDataSet()
        betListAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.layoutManager = LinearLayoutManager(activity)
        val topSpacingDecorator = TopSpacingItemDecoration(30)
        recycler_view.addItemDecoration(topSpacingDecorator)
        betListAdapter = BetsListRecyclerAdapter()
        recycler_view.adapter = betListAdapter
    }

}