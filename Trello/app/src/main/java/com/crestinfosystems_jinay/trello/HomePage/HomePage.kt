package com.crestinfosystems_jinay.trello.HomePage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.crestinfosystems_jinay.trello.HomePage.ProfileScreen.Profile_Screen
import com.crestinfosystems_jinay.trello.HomePage.subScreens.Screen_1
import com.crestinfosystems_jinay.trello.HomePage.subScreens.Screen_2
import com.crestinfosystems_jinay.trello.HomePage.subScreens.Setting_tab
import com.crestinfosystems_jinay.trello.R
import com.crestinfosystems_jinay.trello.databinding.ActivityHomePageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.squareup.picasso.Picasso


class HomePage : AppCompatActivity() {
    val user = Firebase.auth.currentUser
    var binding: ActivityHomePageBinding? = null
    val firstFragment = Screen_1()
    val secondFragment = Screen_2()
    val settingFragment = Setting_tab()
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        setCurrentFragment(firstFragment)
//        binding?.logOutBtn?.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            this.finish()
//        }
        binding?.userProfileImage?.setOnClickListener {
            var intent = Intent(this,Profile_Screen::class.java)
            startActivity(intent)
        }
        setBottomNavBarListner()
        setUserData()
    }

    private fun setUserData() {
        user?.let {
            Log.e("Image URL", it.photoUrl.toString());
            binding?.userName?.text = it.displayName
            binding?.userEmail?.text = it.email
            Picasso.get().load(it.photoUrl.toString()).into(binding?.userProfileImage)
        }
    }

    private fun setBottomNavBarListner() {
        binding?.tab1?.setOnClickListener {
            setCurrentFragment(firstFragment)
            setScreenIndicator(1)
        }
        binding?.tab2?.setOnClickListener {
            setCurrentFragment(secondFragment)
            setScreenIndicator(2)
        }
        binding?.tab3?.setOnClickListener {
            setCurrentFragment(firstFragment)
            setScreenIndicator(3)
        }
        binding?.tab4?.setOnClickListener {
            setCurrentFragment(settingFragment)
            setScreenIndicator(4)
        }
    }

    private fun setScreenIndicator(currentScreen: Int) {
        when (currentScreen) {
            1 -> {
                binding?.tab1Indicator?.visibility = View.VISIBLE
                binding?.tab2Indicator?.visibility = View.GONE
                binding?.tab3Indicator?.visibility = View.GONE
                binding?.tab4Indicator?.visibility = View.GONE
            }

            2 -> {
                binding?.tab1Indicator?.visibility = View.GONE
                binding?.tab3Indicator?.visibility = View.GONE
                binding?.tab4Indicator?.visibility = View.GONE
                binding?.tab2Indicator?.visibility = View.VISIBLE
            }

            3 -> {
                binding?.tab2Indicator?.visibility = View.GONE
                binding?.tab1Indicator?.visibility = View.GONE
                binding?.tab4Indicator?.visibility = View.GONE
                binding?.tab3Indicator?.visibility = View.VISIBLE
            }

            4 -> {
                binding?.tab2Indicator?.visibility = View.GONE
                binding?.tab3Indicator?.visibility = View.GONE
                binding?.tab1Indicator?.visibility = View.GONE
                binding?.tab4Indicator?.visibility = View.VISIBLE
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

}