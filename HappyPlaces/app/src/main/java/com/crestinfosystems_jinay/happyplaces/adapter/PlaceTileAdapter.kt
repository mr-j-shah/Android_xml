package com.crestinfosystems_jinay.happyplaces.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.happyplaces.databinding.HappyPlacesTilesBinding

class PlaceTileAdapter(var item: List<Int>) : RecyclerView.Adapter<PlaceTileAdapter.ViewHolder>() {
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
                binding.title.text = item[position].toString()
            }
        }
    }
}