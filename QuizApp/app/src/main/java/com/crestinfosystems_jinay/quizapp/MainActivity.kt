package com.crestinfosystems_jinay.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart: Button = findViewById(R.id.btn_start)
        val nameTextField: AppCompatEditText = findViewById(R.id.name_text_field)
        btnStart.setOnClickListener {
            if (nameTextField.text!!.isNotEmpty()) {
                val intent = Intent(this@MainActivity,QuizQuestionActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            else{
                Toast.makeText(this,"Please enter Name to move forward",Toast.LENGTH_LONG).show()
            }
        }
    }
}