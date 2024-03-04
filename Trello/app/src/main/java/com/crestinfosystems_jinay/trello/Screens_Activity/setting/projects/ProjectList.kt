package com.crestinfosystems_jinay.trello.Screens_Activity.setting.projects

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.trello.adapter.BoardRecycleViewAd
import com.crestinfosystems_jinay.trello.databinding.ActivityProjectListBinding
import com.crestinfosystems_jinay.trello.network.readProjectsByUserEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectList : AppCompatActivity() {

    var binding: ActivityProjectListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProjectListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerViewAdapter?.layoutManager = layoutManager
        fetchData()
        setContentView(binding?.root)
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            var list = readProjectsByUserEmail("shahjinay02@gmail.com")
            withContext(Dispatchers.Main) {
                binding?.progressCircular?.visibility = View.GONE
                binding?.recyclerViewAdapter?.visibility = View.VISIBLE
                var baoardAdapter = list?.let { BoardRecycleViewAd(it) }
                binding?.recyclerViewAdapter?.adapter = baoardAdapter

            }
        }
    }
}