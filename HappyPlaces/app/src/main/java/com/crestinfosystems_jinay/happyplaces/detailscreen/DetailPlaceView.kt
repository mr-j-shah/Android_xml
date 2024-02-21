package com.crestinfosystems_jinay.happyplaces.detailscreen

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.happyplaces.databinding.ActivityDetailPlaceViewBinding
import com.crestinfosystems_jinay.happyplaces.model.HappyPlace

class DetailPlaceView : AppCompatActivity() {
    var binding: ActivityDetailPlaceViewBinding? = null
    var place: HappyPlace? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailPlaceViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        setSupportActionBar(binding?.applicationToolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.applicationToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        if (intent.hasExtra("place")) {
            // get the Serializable data model class with the details in it
            place =
                intent.getSerializableExtra("place") as HappyPlace
        }
        binding?.placeImage?.setImageURI(Uri.parse(place?.imageURL))
        binding?.detailViewTitle?.text = place?.title
        binding?.detailViewDescription?.text = place?.description
    }
}