package com.crestinfosystems_jinay.happyplaces.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.happyplaces.databinding.HappyPlacesTilesBinding
import com.crestinfosystems_jinay.happyplaces.detailscreen.DetailPlaceView
import com.crestinfosystems_jinay.happyplaces.model.HappyPlace

class PlaceTileAdapter(
    var item: List<HappyPlace>,
    private var context: Context,
) :
    RecyclerView.Adapter<PlaceTileAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: HappyPlacesTilesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HappyPlacesTilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(item[position]) {
                binding.title.text = item[position].title.toString()
                binding.description.text = item[position].description.toString()
                binding.profileImage.setImageURI(Uri.parse(item[position].imageURL))
                binding.cardView.setOnClickListener {
                    val intent = Intent(context, DetailPlaceView::class.java)
                    intent.putExtra("place", item[position])
                    startActivity(context, intent, null)
                }
            }
        }
    }
}