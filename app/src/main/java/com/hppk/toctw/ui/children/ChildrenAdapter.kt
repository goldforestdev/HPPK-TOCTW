package com.hppk.toctw.ui.children

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Gender
import kotlinx.android.synthetic.main.item_child.view.*

class ChildrenAdapter(
    val children: MutableList<Child> = mutableListOf()
) : RecyclerView.Adapter<ChildrenAdapter.ChildHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChildHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false))

    override fun getItemCount() = children.size

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        val child = children[position]
        holder.itemView.tvChildName.text = child.name
        val gender= when(child.gender) {
            Gender.BOY -> R.drawable.ic_boy
            else -> R.drawable.ic_girl
        }
        holder.itemView.ivGender.setImageResource(gender)
    }

    class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}