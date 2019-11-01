package com.hppk.toctw.ui.children

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import kotlinx.android.synthetic.main.item_add_child.view.*
import kotlinx.android.synthetic.main.item_child.view.*

private const val VIEW_TYPE_ADD = 0
private const val VIEW_TYPE_KID = 1

class ChildrenAdapter(
    val children: MutableList<Child> = mutableListOf(),
    private val addChildListener: AddChildClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface AddChildClickListener {
        fun onAvatarClicked()
        fun saveChild(name: String, avatarResId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_ADD -> AddChildHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_add_child,
                parent,
                false
            )
        )
        else -> ChildHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_child,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = children.size

    override fun getItemViewType(position: Int): Int = when {
        children[position].name.isEmpty() -> VIEW_TYPE_ADD
        else -> VIEW_TYPE_KID
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AddChildHolder) {
            bindAddView(holder, position)
        } else {
            bindChild(holder as ChildHolder, position)
        }
    }

    private fun bindAddView(holder: AddChildHolder, position: Int) {
        if (position == 0) {
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.marginStart = 48
            holder.itemView.layoutParams = layoutParams
        }

        if (position == children.lastIndex) {
            val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
            layoutParams.marginEnd = 48
            holder.itemView.layoutParams = layoutParams
        }

        val child = children[position]
        if (child.avatar > 0) {
            holder.itemView.ivUnknownAvatar.setImageResource(child.avatar)
        }

        holder.itemView.ivUnknownAvatar.setOnClickListener { addChildListener.onAvatarClicked() }
        holder.itemView.etName.addTextChangedListener {
            holder.itemView.btnDone.visibility = when {
                it.toString().isEmpty() -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
        holder.itemView.btnDone.setOnClickListener {
            holder.itemView.etName.clearFocus()
            addChildListener.saveChild(holder.itemView.etName.text.toString(), child.avatar)

            holder.itemView.etName.setText("")
            holder.itemView.ivUnknownAvatar.setImageResource(R.drawable.ic_unknown_kid)
        }
    }

    private fun bindChild(holder: ChildHolder, position: Int) {
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        layoutParams.marginStart = if (position == 0) 192 else 0
        holder.itemView.layoutParams = layoutParams

        val child = children[position]
        holder.itemView.tvChildName.text = child.name

        val avatarResId = if (child.avatar == 0) R.drawable.ic_boy else child.avatar
        holder.itemView.ivAvatar.setImageResource(avatarResId)
    }

    class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class AddChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}