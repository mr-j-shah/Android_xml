package com.crestinfosystems_jinay.trello.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.trello.data.Task
import com.crestinfosystems_jinay.trello.databinding.TaskCardTileBinding

class TaskAdapter(
    private var items: ArrayList<Task>,


    ) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: TaskCardTileBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            TaskCardTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            // TODO ADD Detail View Task Screen
        }
        with(holder) {
            with(items[position]) {
                binding.boardTitle.text = items[position].title
                binding.boardDesc.text = items[position].desc
                binding.state.text = items[position].state.toString().uppercase()
                binding.lastEditBy.text = items[position].lastEdit
            }
        }
    }

    fun submitList(newData: ArrayList<Task>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }
}