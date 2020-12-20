package pw.wpam.polityper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_available_leagues.*
import pw.wpam.polityper.adapters.LeagueListRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration

class AvailableLeaguesActivity : AppCompatActivity() {

    private lateinit var leagueListAdapter: LeagueListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_leagues)

        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        leagueListAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@AvailableLeaguesActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            leagueListAdapter = LeagueListRecyclerAdapter(this@AvailableLeaguesActivity)
            adapter = leagueListAdapter
        }
    }

}