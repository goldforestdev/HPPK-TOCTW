package com.hppk.toctw.ui.children

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.common.getAvatarResId
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.DEFAULT_AVATAR
import com.hppk.toctw.data.model.DEFAULT_UNKNOWN_AVATAR
import kotlinx.android.synthetic.main.item_child.view.*


class ChildrenAdapter(
    private val context: Context,
    val children: MutableList<Child> = mutableListOf(),
    var viewWidth: Int = 1080,
    private val childListener: ChildClickListener
) : RecyclerView.Adapter<ChildrenAdapter.ChildHolder>() {

    interface ChildClickListener {
        fun onChildClicked(imageView: ImageView, child: Child)
        fun deleteChild(child: Child)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChildHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_child,
            parent,
            false
        )
    )

    override fun getItemCount() = children.size

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        holder.itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val sideMargin = (viewWidth / 2) - (holder.itemView.measuredWidth / 2)
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams

        if (position == 0) {
            layoutParams.marginStart = sideMargin
            holder.itemView.layoutParams = layoutParams
        }

        if (position == children.lastIndex) {
            layoutParams.marginEnd = sideMargin
            holder.itemView.layoutParams = layoutParams
        }


        val child = children[position]
        holder.itemView.tvChildName.text = child.name

        val avatarResName = if (child.avatar == DEFAULT_UNKNOWN_AVATAR) DEFAULT_AVATAR else child.avatar
        holder.itemView.ivAvatar.setImageResource(context.getAvatarResId(avatarResName))
        holder.itemView.ivDeleteChild.setOnClickListener {
            childListener.deleteChild(child)
        }
        holder.itemView.setOnClickListener {
            childListener.onChildClicked(holder.itemView.ivAvatar, child)
        }
    }

    class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
