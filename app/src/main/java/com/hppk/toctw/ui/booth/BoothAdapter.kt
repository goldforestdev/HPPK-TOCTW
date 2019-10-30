package com.hppk.toctw.ui.booth

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Booth
import kotlinx.android.synthetic.main.item_booth_list.view.*


class BoothAdapter(
    val booths : MutableList<Booth> = mutableListOf(),
    private var context : Context? = null,
    private val boothClickLister: BoothClickLister
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return SchedulesHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_booth_list, parent, false))
    }

    interface BoothClickLister {
        fun onBoothClick(booth: Booth)
    }

    override fun getItemCount(): Int  = booths.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SchedulesHolder) {
            with (holder) {
                tvBooth.text = booths[position].title
                tvLocation.text = booths[position].location

                itemView.setOnClickListener {
                    boothClickLister.onBoothClick(booths[position])
                }
            }

        }
    }


    class SchedulesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvBooth : TextView = itemView.tvBooth
        val tvLocation : TextView = itemView.tvLocation
        val ivStar : ImageView = itemView.ivStar
    }
}