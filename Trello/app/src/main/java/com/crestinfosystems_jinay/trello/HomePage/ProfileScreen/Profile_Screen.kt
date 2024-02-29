package com.crestinfosystems_jinay.trello.HomePage.ProfileScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.trello.data.UserData
import com.crestinfosystems_jinay.trello.databinding.ActivityProfileScreenBinding
import com.crestinfosystems_jinay.trello.network.FirestoreDatabase
import com.crestinfosystems_jinay.trello.splash_pages.UserDataForm.UserDataForm
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Profile_Screen : AppCompatActivity() {
    var binding: ActivityProfileScreenBinding? = null
    var user = Firebase.auth.currentUser
    var db: FirestoreDatabase = FirestoreDatabase()
    lateinit var data: UserData
    var isDataFetched: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        binding?.popUpBack?.setOnClickListener {
            onBackPressed()
        }
        binding?.userName?.text = user?.displayName
        binding?.userEmail?.text = user?.email
        binding?.editBtn?.setOnClickListener {
            if (isDataFetched) {
                var intent = Intent(this, UserDataForm::class.java)
                intent.putExtra("mobile_num", data.mobileNumber)
                intent.putExtra("organization", data.organization)
                intent.putExtra("email", data.email)
                startActivity(intent)
            }
        }
        Picasso.get().load(user?.photoUrl.toString()).into(binding?.userProfileImage)
        setContentView(binding?.root)
        CoroutineScope(Dispatchers.IO).launch {
            // Simulate data processing or fetching
            data = db.readUser(user?.email!!)!!
            Log.d("User Data", data.toString())
            // Switch to the main thread to update the UI
            withContext(Dispatchers.Main) {
//                binding?.editBtn?.isClickable = true;
                isDataFetched = true;
                binding?.shimmerLayoutMobile?.visibility = View.GONE
                binding?.shimmerLayoutOrganization?.visibility = View.GONE
                binding?.userMobileNum?.visibility = View.VISIBLE
                binding?.userMobileNum?.text = data.mobileNumber
                binding?.userOrganization?.visibility = View.VISIBLE
                binding?.userOrganization?.text = data.organization
            }
        }
    }
}