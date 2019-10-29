package com.hppk.toctw.ui.schedules

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Booth


class SchedulesAdapter(
    private var context : Context? = null,
    private val boothClickLister: BoothClickLister,
    private val booths : MutableList<Booth> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return BusLinesHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bus_list, parent, false))
    }

    interface BoothClickLister {
        fun onBoothClick(booth: Booth)
    }

    override fun getItemCount(): Int  = booths.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BusLinesHolder) {
            with (holder) {
                tvBusLineName.text = busLines[position].busLineName
                tvBusStart.text = "${busLines[position].busStops[0].busStopName} ${context!!.resources.getString(R.string.start)}"
                itemView.setOnClickListener {
                    busLineClickListener.onBusLineClick(busLines[position])
                }

                if (favorites.contains(busLines[position]) ) {
                    ivStar.setImageResource(R.drawable.ic_star_selected)
                } else {
                    ivStar.setImageResource(R.drawable.ic_star_border)
                }

                ivStar.setOnClickListener {
                    busFavoritesClickListener.onBusFavoritesClick(busLines[position])
                }
            }
        }
    }


    class BusLinesHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvBusLineName : TextView = itemView.tvBusLineName
        val tvBusStart : TextView = itemView.tvBusStart
        val ivStar : ImageView = itemView.ivStar
    }
}