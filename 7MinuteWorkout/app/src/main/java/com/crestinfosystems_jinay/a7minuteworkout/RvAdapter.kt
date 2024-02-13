package com.crestinfosystems_jinay.a7minuteworkout

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.a7minuteworkout.databinding.ItemExerciseStatusBinding


class RvAdapter(
    var languageList: List<Exercise>, var currentExercise :Int
) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemExerciseStatusBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(languageList[position]){
                if (position == currentExercise ){
                    binding.tv.setBackgroundResource(R.drawable.item_circular_color_primary_background)
                }
                binding.tv.text = this.id.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return languageList.size
    }
}
