package com.crestinfosystems_jinay.a7minuteworkout


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.crestinfosystems_jinay.a7minuteworkout.data.historydata.History
import com.crestinfosystems_jinay.a7minuteworkout.databinding.ActivityResultBinding
import kotlinx.coroutines.launch


class ResultActivity : AppCompatActivity() {

    private var binding: ActivityResultBinding? = null
    private var db = Graph.wishRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            Log.d("Time Stamp Current", System.currentTimeMillis().toString())
            db.insertHistory(History(timestamp = System.currentTimeMillis().toString()))
        }
        binding = ActivityResultBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.shareButton?.setOnClickListener {
            shareTextWithImage(
                "Just completed a 7-minute workout! \uD83D\uDCAA\uD83C\uDFCB\uFE0F\u200D♀\uFE0F Feeling energized and accomplished. #FitnessJourney #WorkoutChallenge #daily7minuteworkout",
                R.drawable.img_main_page
            )
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun shareTextWithImage(textToShare: String, image: Int) {
        var shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("image/*")

        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare)
        var imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + image)

        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)

        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val chooserIntent = Intent.createChooser(shareIntent, "Share via")
        shareIntent.setType("image/png")
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(chooserIntent)
        }
    }
}