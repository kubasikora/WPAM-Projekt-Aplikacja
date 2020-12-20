package pw.wpam.polityper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_available_leagues.*
import pw.wpam.polityper.adapters.BetsListRecyclerAdapter
import pw.wpam.polityper.adapters.TopSpacingItemDecoration

class BettingActivity : AppCompatActivity() {

    private lateinit var betListAdapter: BetsListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_betting)

        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet(){
        val data = DataSourceBetting.createDataSet()
        betListAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@BettingActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            betListAdapter = BetsListRecyclerAdapter()
            adapter = betListAdapter
        }
    }
}