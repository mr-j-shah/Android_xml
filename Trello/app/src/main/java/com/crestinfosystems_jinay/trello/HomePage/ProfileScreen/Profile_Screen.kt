package com.crestinfosystems_jinay.trello.HomePage.ProfileScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.trello.databinding.ActivityProfileScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class Profile_Screen : AppCompatActivity() {
    var binding: ActivityProfileScreenBinding? = null
    var user = Firebase.auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        binding?.popUpBack?.setOnClickListener {
            onBackPressed()
        }
        binding?.userName?.text = user?.displayName
        binding?.userEmail?.text = user?.email
        Picasso.get().load(user?.photoUrl.toString()).into(binding?.userProfileImage)
        setContentView(binding?.root)
    }
}