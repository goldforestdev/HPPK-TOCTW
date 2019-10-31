package com.hppk.toctw.ui.children.avatars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import kotlinx.android.synthetic.main.item_avatar.view.*

class AvatarsAdapter(
    private val avatars: List<Int>,
    private val listener: AvatarClickListener
) : RecyclerView.Adapter<AvatarsAdapter.AvatarHolder>() {

    interface AvatarClickListener {
        fun onAvatarClicked(resId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AvatarHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
    )

    override fun getItemCount(): Int = avatars.size

    override fun onBindViewHolder(holder: AvatarHolder, position: Int) {
        val resId = avatars[position]
        holder.itemView.ivAvatar.setImageResource(resId)
        holder.itemView.ivAvatar.setOnClickListener {
            listener.onAvatarClicked(resId)
        }
    }

    class AvatarHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}