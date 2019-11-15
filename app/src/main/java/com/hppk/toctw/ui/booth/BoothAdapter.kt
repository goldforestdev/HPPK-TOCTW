package com.hppk.toctw.ui.booth

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.hppk.toctw.R
import com.hppk.toctw.auth.AppAuth
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Busy
import com.hppk.toctw.data.model.Favorites
import kotlinx.android.synthetic.main.item_booth_list.view.*


const val VIEW_TYPE_PHOTO = 0
const val VIEW_TYPE_LIST = 1

class BoothAdapter(
    val booths: MutableList<Booth> = mutableListOf(),
    val favorites : MutableList<String> = mutableListOf(),
    var viewType: Int = VIEW_TYPE_PHOTO,
    private var context: Context? = null,
    private val boothClickLister: BoothClickLister,
    private val busyClickLister: BusyClickLister,
    private val stampClickLister: StampClickLister,
    private val favoritesClickLister : FavoritesClickLister
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return SchedulesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_booth_list,
                parent,
                false
            )
        )
    }

    interface BoothClickLister {
        fun onBoothClick(booth: Booth)
    }

    interface BusyClickLister {
        fun onBusyClick(booth: Booth)
    }

    interface StampClickLister {
        fun onStampClick(booth: Booth)
    }

    interface FavoritesClickLister {
        fun onFavoritesClick(booth: Booth, position: Int)
    }

    override fun getItemCount(): Int = booths.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SchedulesHolder) {
            with(holder) {
                val booth = booths[position]

                if (viewType == VIEW_TYPE_PHOTO) {
                    val imgResId = context!!.resources.getIdentifier(booth.photoRes, "drawable", context!!.packageName)
                    ivPhoto.setImageResource(imgResId)
                    ivPhoto.visibility = View.VISIBLE
                } else {
                    ivPhoto.visibility = View.GONE
                }

                tvBooth.text = booth.title
                cpLocation.text = booth.location

                setLocationViewColor(position)
                setBusyView(position)

                if (booth.isStamp) ivStamp.visibility = View.VISIBLE else ivStamp.visibility = View.GONE

                if (favorites.contains(booth.id)) {
                    ivStar.setImageResource(R.drawable.ic_star_selected)
                } else {
                    ivStar.setImageResource(R.drawable.ic_star_border)
                }

                ivStar.setOnClickListener {
                    favoritesClickLister.onFavoritesClick(booth, position)
                }

                viewBusy.setOnClickListener {
                    setBoothBusyClickListener(position)
                }

                tvBusy.setOnClickListener {
                    setBoothBusyClickListener(position)
                }

                ivStamp.setOnClickListener {
                    stampClickLister.onStampClick(booth)
                }

                itemView.setOnClickListener {
                    boothClickLister.onBoothClick(booth)
                }
            }
        }
    }

    private fun setBoothBusyClickListener(position: Int) {
        if (AppAuth.isStaff) {
            busyClickLister.onBusyClick(booths[position])
        } else {
            boothClickLister.onBoothClick(booths[position])
        }
    }

    private fun SchedulesHolder.setLocationViewColor(position: Int) {
        val locationColorRes = booths[position].locationColorRes
        if (locationColorRes.isNotEmpty()) {
            var id = context!!.resources.getIdentifier(locationColorRes, "color", context!!.packageName)
            if (id == 0) {
                id = R.color.five_color
            }
            cpLocation.setChipBackgroundColorResource(id)
            cpLocation.setOnClickListener {
                boothClickLister.onBoothClick(booths[position])
            }
        }
    }

    private fun SchedulesHolder.setBusyView(position: Int) {
        when (booths[position].busy) {
            Busy.GOOD -> {
                viewBusy.setBackgroundResource(android.R.color.holo_green_dark)
                tvBusy.setTextColor(getColorWrapper(context!!, android.R.color.holo_green_dark))
                tvBusy.text = context!!.getString(R.string.busy_good)
            }

            Busy.NORMAL -> {
                viewBusy.setBackgroundResource(R.color.darkYellow)
                tvBusy.setTextColor(getColorWrapper(context!!, R.color.darkYellow))
                tvBusy.text = context!!.getString(R.string.busy_normal)
            }

            Busy.VERY_BUSY -> {
                viewBusy.setBackgroundResource(android.R.color.holo_red_dark)
                tvBusy.setTextColor(getColorWrapper(context!!, android.R.color.holo_red_dark))
                tvBusy.text = context!!.getString(R.string.busy_very)
            }

            Busy.CLOSE -> {
                viewBusy.setBackgroundResource(android.R.color.darker_gray)
                tvBusy.setTextColor(getColorWrapper(context!!, android.R.color.darker_gray))
                tvBusy.text = context!!.getString(R.string.busy_close)
            }
        }
    }

    private fun getColorWrapper(context: Context, id: Int): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getColor(id)
        } else {
            context.resources.getColor(id)
        }
    }

    class SchedulesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPhoto: ImageView = itemView.ivPhoto
        val viewBusy: View = itemView.viewBusy
        val tvBooth: TextView = itemView.tvBooth
        val cpLocation: Chip = itemView.cpLocation
        val tvBusy: TextView = itemView.tvBusy
        val ivStamp: ImageView = itemView.ivStamp
        val ivStar: ImageView = itemView.ivStar
    }
}