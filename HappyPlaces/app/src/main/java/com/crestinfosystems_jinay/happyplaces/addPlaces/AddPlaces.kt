package com.crestinfosystems_jinay.happyplaces.addPlaces

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.happyplaces.databinding.ActivityAddPlacesBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar

class AddPlaces : AppCompatActivity() {
    private var binding: ActivityAddPlacesBinding? = null
    private var selectedDate: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddPlacesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build()
        setSupportActionBar(binding?.applicationToolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.applicationToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.textfield?.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog and set the initial date to the current date
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Handle the selected date
                selectedDate = "$year-${month + 1}-$dayOfMonth"
                binding?.textfield?.setText(selectedDate)
                // You can perform any action with the selected date here
                // For example, update a TextView with the selected date
                // textView.text = selectedDate
            },
            currentYear,
            currentMonth,
            currentDay
        )

        // Show the date picker dialog
        datePickerDialog.show()
    }

}