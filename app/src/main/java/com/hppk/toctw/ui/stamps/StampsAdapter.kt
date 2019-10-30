package com.hppk.toctw.ui.stamps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Stamp
import kotlinx.android.synthetic.main.item_stamp.view.*

class StampsAdapter (
    val stamps: MutableList<Stamp> = mutableListOf()
): RecyclerView.Adapter<StampsAdapter.StampHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampHolder {
        return StampHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stamp, parent, false))
    }

    override fun getItemCount() = stamps.size

    override fun onBindViewHolder(holder: StampHolder, position: Int) {
        val stamp = stamps[position]

        holder.itemView.tvBoothName.text = stamp.boothName

        when (stamp.isStamps) {
            true -> R.drawable.ic_stamp_hp
            else -> R.drawable.ic_stamp_hp_empty
        }.let {
            holder.itemView.ivStamp.setImageResource(it)
        }
    }

    class StampHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}