package com.crestinfosystems_jinay.trello.HomePage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.trello.MainActivity
import com.crestinfosystems_jinay.trello.R
import com.google.firebase.auth.FirebaseAuth

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val logOutBtn: Button = findViewById(R.id.log_out_btn)
        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}