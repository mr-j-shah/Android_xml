package com.crestinfosystems_jinay.a7minuteworkout.data.historydata

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.a7minuteworkout.databinding.HistoryItemTileBinding
import java.sql.Date
import java.text.SimpleDateFormat

class HistoryViewAdepter(private var items: List<History>) :
    RecyclerView.Adapter<HistoryViewAdepter.ViewHolder>() {

    inner class ViewHolder(var binding: HistoryItemTileBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            HistoryItemTileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                if (position % 2 != 0) {
                    binding.historyDataTile.setBackgroundColor(Color.WHITE)
                }
                try {

                    val sdfDate = SimpleDateFormat("dd/MM/yyyy")
                    val netDate = Date(items[position].timestamp!!.toLong())
                    binding.historyTileDate.text = sdfDate.format(netDate)
                    val sdfTime = SimpleDateFormat("hh:mm a")
                    val netTime = Date(items[position].timestamp!!.toLong())
                    binding.historyTileTime.text = sdfTime.format(netTime)
                } catch (e: Exception) {
                    Log.e("Error", e.toString())
                }
            }
        }
    }

    fun submitList(newData: List<History>) {
        items = newData
        notifyDataSetChanged()
    }
}