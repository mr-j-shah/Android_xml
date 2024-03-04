package com.crestinfosystems_jinay.trello.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.trello.data.Board
import com.crestinfosystems_jinay.trello.databinding.BoardCardTileBinding

class BoardRecycleViewAd(private var items: List<Board>) :
    RecyclerView.Adapter<BoardRecycleViewAd.ViewHolder>() {

    inner class ViewHolder(var binding: BoardCardTileBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            BoardCardTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.boardTitle.text = items[position].name
                binding.boardDesc.text = items[position].des
                binding.tileNextScreen.setOnClickListener {

                }
            }
        }
    }

    fun submitList(newData: List<Board>) {
        items = newData
        notifyDataSetChanged()
    }
}