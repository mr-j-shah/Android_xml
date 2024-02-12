package com.crestinfosystems_jinay.a7minuteworkout


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.a7minuteworkout.databinding.ActivityResultBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ResultActivity : AppCompatActivity() {

    private var binding: ActivityResultBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
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
            shareTextWithImage("Just completed a 7-minute workout! \uD83D\uDCAA\uD83C\uDFCB\uFE0F\u200D♀\uFE0F Feeling energized and accomplished. #FitnessJourney #WorkoutChallenge #daily7minuteworkout",R.drawable.img_main_page)
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
//        val bitmap = BitmapFactory.decodeResource(resources, image)
//        var path =  "/Share.png"
//        var out: OutputStream? = null
//        val file = File(path)
//        try {
//            out = FileOutputStream(file)
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
//            out.flush()
//            out.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        path = file.path
//        val bmpUri = Uri.parse("file://$path")
//        shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
//        shareIntent.putExtra(
//            Intent.EXTRA_TEXT,
//            "Hey please check this application https://play.google.com/store/apps/details?id=$packageName"
//        )
//        shareIntent.setType("image/png")
//        startActivity(Intent.createChooser(shareIntent, "Share with"))
    }
}