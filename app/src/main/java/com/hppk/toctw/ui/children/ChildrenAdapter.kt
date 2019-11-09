package com.hppk.toctw.ui.children

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import kotlinx.android.synthetic.main.item_child.view.*


class ChildrenAdapter(
    val children: MutableList<Child> = mutableListOf(),
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
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        Log.d("TEST", "[TOCTW] bindChild - width: ${layoutParams.width}")
        layoutParams.marginStart = if (position == 0) 120 else 0
        holder.itemView.layoutParams = layoutParams

        val child = children[position]
        holder.itemView.tvChildName.text = child.name

        val avatarResId = if (child.avatar == 0) R.drawable.ic_boy else child.avatar
        holder.itemView.ivAvatar.setImageResource(avatarResId)
        holder.itemView.ivDeleteChild.setOnClickListener {
            childListener.deleteChild(child)
        }
        holder.itemView.setOnClickListener {
            childListener.onChildClicked(holder.itemView.ivAvatar, child)
        }
    }

    class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}