package com.crestinfosystems_jinay.happyplaces

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.happyplaces.adapter.PlaceTileAdapter
import com.crestinfosystems_jinay.happyplaces.addPlaces.AddPlaces
import com.crestinfosystems_jinay.happyplaces.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.floatingActionButton?.setOnClickListener {
            val intent = Intent(this@MainActivity, AddPlaces::class.java)
            startActivity(intent)
        }
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerViewAdapter?.layoutManager = layoutManager
        val tileAdapter = PlaceTileAdapter(listOf(1, 2, 3, 4, 5))
        binding?.recyclerViewAdapter?.adapter = tileAdapter
    }
}