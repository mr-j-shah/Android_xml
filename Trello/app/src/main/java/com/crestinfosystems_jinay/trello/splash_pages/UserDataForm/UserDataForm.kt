package com.crestinfosystems_jinay.trello.splash_pages.UserDataForm

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.trello.data.UserData
import com.crestinfosystems_jinay.trello.databinding.ActivityUserDataFormBinding
import com.crestinfosystems_jinay.trello.network.FirestoreDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDataForm : AppCompatActivity() {
    var binding: ActivityUserDataFormBinding? = null
    var db = FirestoreDatabase()
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LogIn", "onCreate: ${intent.getStringExtra("email")} ")
        binding = ActivityUserDataFormBinding.inflate(layoutInflater)
        binding?.popUpBack?.setOnClickListener { onBackPressed() }
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.textInputLayoutMobilenumEdit?.setOnFocusChangeListener { v, hasFocus ->
            binding?.textInputLayoutMobilenum?.isHintEnabled = !hasFocus
        }
        binding?.textInputLayoutOrganizationEdit?.setOnFocusChangeListener { v, hasFocus ->
            binding?.textInputLayoutOrganization?.isHintEnabled = !hasFocus
        }
        binding?.updateBtn?.setOnClickListener {
            Toast.makeText(
                this,
                "Mobile :: ${binding?.textInputLayoutMobilenumEdit?.text.toString()}",
                Toast.LENGTH_SHORT
            ).show()

            CoroutineScope(Dispatchers.IO).launch {
//                db.writeNewUser(
//                    user = UserData(
//                        email = intent.getStringExtra("email"),
//                        name = null,
//                        mobileNumber = binding?.textInputLayoutMobilenumEdit?.text.toString(),
//                        organization = binding?.textInputLayoutOrganizationEdit?.text.toString()
//                    )
//                )
                withContext(Dispatchers.Main) {
                }
            }
        }
    }

    private fun String?.toEditable(): Editable? {
        return Editable.Factory.getInstance().newEditable(this)
    }
}