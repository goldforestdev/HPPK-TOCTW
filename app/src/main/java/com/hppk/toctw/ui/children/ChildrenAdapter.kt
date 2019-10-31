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
        holder.itemView.ivUnknownAvatar.setOnClickListener { addChildListener.onAvatarClicked() }
        holder.itemView.etName.addTextChangedListener {
            holder.itemView.btnDone.visibility = when {
                it.toString().isNullOrEmpty() -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
        holder.itemView.btnDone.setOnClickListener {
//            presenter.saveChild(holder.itemView.etName.text.toString(), gender)
        }
    }

    private fun bindChild(holder: ChildHolder, position: Int) {
        val child = children[position]
        holder.itemView.tvChildName.text = child.name
        holder.itemView.ivAvatar.setImageResource(child.avatar)
    }

    class ChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class AddChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}