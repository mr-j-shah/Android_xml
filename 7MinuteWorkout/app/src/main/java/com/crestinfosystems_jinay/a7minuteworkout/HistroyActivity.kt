package com.crestinfosystems_jinay.a7minuteworkout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.a7minuteworkout.databinding.ActivityHistroyBinding

class HistroyActivity : AppCompatActivity() {
    private var binding: ActivityHistroyBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {

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
    }
}