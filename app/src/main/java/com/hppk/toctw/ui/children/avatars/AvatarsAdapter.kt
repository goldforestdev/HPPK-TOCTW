package com.hppk.toctw.ui.children.avatars

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.common.getAvatarResId
import kotlinx.android.synthetic.main.item_avatar.view.*

class AvatarsAdapter(
    private val context: Context,
    private val avatars: List<String>,
    private val listener: AvatarClickListener
) : RecyclerView.Adapter<AvatarsAdapter.AvatarHolder>() {

    interface AvatarClickListener {
        fun onAvatarClicked(resName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AvatarHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_avatar, parent, false)
    )

    override fun getItemCount(): Int = avatars.size

    override fun onBindViewHolder(holder: AvatarHolder, position: Int) {
        val resName = avatars[position]

        holder.itemView.ivAvatar.setImageResource(context.getAvatarResId(resName))
        holder.itemView.ivAvatar.setOnClickListener {
            listener.onAvatarClicked(resName)
        }
    }

    class AvatarHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}