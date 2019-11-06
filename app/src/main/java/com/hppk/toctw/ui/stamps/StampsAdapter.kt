package com.hppk.toctw.ui.stamps

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Stamp
import kotlinx.android.synthetic.main.item_stamp.view.*
import kotlinx.android.synthetic.main.item_stamp_label.view.*


data class StampFlipWrapper(
    val stamp: Stamp,
    var isFlip: Boolean = false
)

const val VIEW_TYPE_LABEL = 0
const val VIEW_TYPE_STAMP = 1

class StampsAdapter(
    val stamps: MutableList<Any> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_LABEL -> LabelHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_stamp_label,
                    parent,
                    false
                )
            )
            else -> StampHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_stamp,
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int): Int = when {
        stamps[position] is Int -> VIEW_TYPE_LABEL
        else -> VIEW_TYPE_STAMP
    }

    override fun getItemCount() = stamps.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LabelHolder) {
            bindLabelHolder(holder, position)
        } else {
            bindStampHolder(holder as StampHolder, position)
        }
    }

    private fun bindLabelHolder(holder: LabelHolder, position: Int) {
        holder.itemView.tvStampLabel.setText(stamps[position] as Int)
    }

    private fun bindStampHolder(holder: StampHolder, position: Int) {
        var (stamp, isFlip) = stamps[position] as StampFlipWrapper

        with(holder.itemView) {
            tvBoothName.text = stamp.boothName
            tvBoothLocation.text = stamp.boothLocation

            when (stamp.isDone) {
                true -> R.drawable.ic_stamp_hp
                else -> R.drawable.ic_stamp_hp_empty
            }.let {
                ivStamp.setImageResource(it)
            }

            setOnClickListener {
                val oa1 = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1f, 0f)
                val oa2 = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0f, 1f)
                oa1.interpolator = DecelerateInterpolator()
                oa2.interpolator = AccelerateDecelerateInterpolator()
                oa1.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)

                        if (isFlip) {
                            ivStamp.visibility = View.VISIBLE
                            tvBoothName.visibility = View.INVISIBLE
                            tvBoothLocation.visibility = View.INVISIBLE
                        } else {
                            ivStamp.visibility = View.INVISIBLE
                            tvBoothName.visibility = View.VISIBLE
                            tvBoothLocation.visibility = View.VISIBLE
                        }

                        isFlip = !isFlip
                        oa2.start()
                    }
                })
                oa1.start()
            }
        }
    }

    class StampHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class LabelHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}