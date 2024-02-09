package com.crestinfosystems_jinay.ageinminute

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonObj : Button = findViewById(R.id.select_date_btn)
        buttonObj.setOnClickListener {
            onSelectDateBtn()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSelectDateBtn(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            Toast.makeText(this,"${dayOfMonth.toString()}/${monthOfYear+1}/${year}",Toast.LENGTH_SHORT).show()
            // Display Selected date in textbox
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            val selectedDate = "${dayOfMonth.toString()}/${monthOfYear+1}/${year}"
            val textViewSelctedDate : TextView = findViewById(R.id.selectedDateView)
            textViewSelctedDate.setText(selectedDate)
            val startDateTime = LocalDateTime.of(year, monthOfYear+1, dayOfMonth, 0, 0)
            val endDateTime = LocalDateTime.now()
            val duration = Duration.between(startDateTime, endDateTime)
            val ageInMinute : TextView = findViewById(R.id.ageInMinuteView)
            ageInMinute.setText((duration.toMinutes()).toString())
        }, year, month, day)
        dpd.datePicker.maxDate = Calendar.getInstance().timeInMillis
        dpd.show()
    }
}