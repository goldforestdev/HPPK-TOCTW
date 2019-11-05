package com.hppk.toctw.ui.stamps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Stamp
import kotlinx.android.synthetic.main.item_stamp.view.*
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator


data class StampFlipWrapper(
    val stamp: Stamp,
    var isFlip: Boolean = false
)

class StampsAdapter(
    val stamps: MutableList<StampFlipWrapper> = mutableListOf(),
    private val listener: MissionClearedListener
) : RecyclerView.Adapter<StampsAdapter.StampHolder>() {

    interface MissionClearedListener {
        fun onMissionCleared()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampHolder {
        return StampHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_stamp,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = stamps.size

    override fun onBindViewHolder(holder: StampHolder, position: Int) {
        var (stamp, isFlip) = stamps[position]

        holder.itemView.tvBoothName.text = stamp.boothName
        holder.itemView.tvBoothName.postDelayed({
            holder.itemView.tvBoothName.isSelected =
                true
        }, 1000)

        holder.itemView.tvBoothLocation.text = stamp.boothLocation

        when (stamp.isDone) {
            true -> R.drawable.ic_stamp_hp
            else -> R.drawable.ic_stamp_hp_empty
        }.let {
            holder.itemView.ivStamp.setImageResource(it)
        }

        holder.itemView.setOnClickListener {
            val oa1 = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1f, 0f)
            val oa2 = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0f, 1f)
            oa1.interpolator = DecelerateInterpolator()
            oa2.interpolator = AccelerateDecelerateInterpolator()
            oa1.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)

                    if (isFlip) {
                        holder.itemView.tvBoothLocation.visibility = View.VISIBLE
                        holder.itemView.tvBoothName.visibility = View.VISIBLE
                        holder.itemView.btnMissionClear.visibility = View.GONE
                    } else {
                        holder.itemView.tvBoothLocation.visibility = View.GONE
                        holder.itemView.tvBoothName.visibility = View.GONE
                        holder.itemView.btnMissionClear.visibility = View.VISIBLE
                    }

                    isFlip = !isFlip
                    oa2.start()
                }
            })
            oa1.start()
        }
        holder.itemView.btnMissionClear.setOnClickListener {
            listener.onMissionCleared()
        }
    }

    class StampHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}