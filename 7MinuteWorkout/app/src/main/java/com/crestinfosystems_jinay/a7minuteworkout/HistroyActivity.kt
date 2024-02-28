package com.crestinfosystems_jinay.a7minuteworkout

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.a7minuteworkout.data.historydata.History
import com.crestinfosystems_jinay.a7minuteworkout.data.historydata.HistoryViewAdepter
import com.crestinfosystems_jinay.a7minuteworkout.databinding.ActivityHistroyBinding
import kotlinx.coroutines.launch

class HistroyActivity : AppCompatActivity() {
    private var binding: ActivityHistroyBinding? = null
    private var historyList: List<History> = listOf()
    private var db = Graph.wishRepository
    var hoistoryAdapter: HistoryViewAdepter = HistoryViewAdepter(historyList)
    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            db.getAllHistory().collect { data ->
                // Update UI or pass data to the adapter
                hoistoryAdapter.submitList(data)
                Log.d("Data from SQLite", data.toString())
            }
        }
//        lifecycleScope.launch {
//            historyList = db.getAllHistory()
//            hoistoryAdapter.submitList(historyList!!)
//            Log.d("Data from SQLite", historyList.toString())
//        }
        binding = ActivityHistroyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerViewAdapter?.layoutManager = layoutManager
        var list: List<History> = listOf(
            History(id = 0, timestamp = "1510500494"),
            History(id = 1, timestamp = "1034534442"),
            History(id = 2, timestamp = "1707898722")
        )
        hoistoryAdapter = HistoryViewAdepter(historyList)
        binding?.recyclerViewAdapter?.adapter = hoistoryAdapter
    }
}