package com.hppk.toctw.ui.children.add

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Gender
import kotlinx.android.synthetic.main.item_child_simple.view.*

class ChildrenAdapter(
    val children: MutableList<Child> = mutableListOf()
) : RecyclerView.Adapter<ChildrenAdapter.ChildHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ChildHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_child_simple,
                parent,
                false
            )
        )

    override fun getItemCount() = children.size

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        val child = children[position]

        holder.itemView.tvName.text = child.name

        val imgRes = when (child.gender) {
            Gender.BOY -> R.drawable.ic_male
            else -> R.drawable.ic_female
        }
        holder.itemView.ivGender.setImageResource(imgRes)
    }

    class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}