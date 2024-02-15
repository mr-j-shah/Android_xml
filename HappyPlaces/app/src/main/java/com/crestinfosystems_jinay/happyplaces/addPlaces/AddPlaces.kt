package com.crestinfosystems_jinay.happyplaces.addPlaces

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.happyplaces.databinding.ActivityAddPlacesBinding

class AddPlaces : AppCompatActivity() {
    private var binding: ActivityAddPlacesBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddPlacesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setSupportActionBar(binding?.applicationToolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.applicationToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}