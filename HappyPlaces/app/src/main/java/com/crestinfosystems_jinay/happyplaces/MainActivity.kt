package com.crestinfosystems_jinay.happyplaces

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.happyplaces.adapter.PlaceTileAdapter
import com.crestinfosystems_jinay.happyplaces.addPlaces.AddPlaces
import com.crestinfosystems_jinay.happyplaces.database.HappyPlaceDatabase
import com.crestinfosystems_jinay.happyplaces.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        getHappyPlacesListFromLocalDB()
        binding?.floatingActionButton?.setOnClickListener {
            val intent = Intent(this@MainActivity, AddPlaces::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        getHappyPlacesListFromLocalDB()
    }

    override fun onStart() {
        super.onStart()
        getHappyPlacesListFromLocalDB()
    }

    private fun getHappyPlacesListFromLocalDB() {

        val dbHandler = HappyPlaceDatabase(this, null)

        val getHappyPlacesList = dbHandler.getAllPlaces()

        if ((getHappyPlacesList?.size ?: 0) > 0) {
            Log.e("Happy Place List List", "getHappyPlacesListFromLocalDB: ${getHappyPlacesList}")
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding?.recyclerViewAdapter?.layoutManager = layoutManager
            val tileAdapter = PlaceTileAdapter(getHappyPlacesList!!,this@MainActivity)
            binding?.recyclerViewAdapter?.adapter = tileAdapter
        } else {
            Toast.makeText(
                this,
                "There is no Happy place Added please add Happy Place",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}