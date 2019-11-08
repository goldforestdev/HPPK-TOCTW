package com.hppk.toctw.ui.children

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.data.model.Child
import kotlinx.android.synthetic.main.item_add_child.view.*
import kotlinx.android.synthetic.main.item_child.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.hppk.toctw.R


private const val VIEW_TYPE_ADD = 0
private const val VIEW_TYPE_KID = 1

class ChildrenAdapter(
    val children: MutableList<Child> = mutableListOf(),
    private val childListener: ChildClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ChildClickListener {
        fun onAvatarClicked()
        fun saveChild(name: String, avatarResId: Int)
        fun onChildClicked(imageView: ImageView, child: Child)
        fun deleteChild(child: Child)
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
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
        Log.d("TEST", "[TOCTW] bindAddView - width: ${layoutParams.width}")
        if (position == 0) {
            layoutParams.marginStart = 48
            holder.itemView.layoutParams = layoutParams
        }

        if (position == children.lastIndex) {
            layoutParams.marginEnd = 48
            holder.itemView.layoutParams = layoutParams
        }

        val child = children[position]
        if (child.avatar > 0) {
            holder.itemView.ivUnknownAvatar.setImageResource(child.avatar)
        }

        holder.itemView.ivUnknownAvatar.setOnClickListener { childListener.onAvatarClicked() }
        holder.itemView.etName.addTextChangedListener {
            holder.itemView.btnDone.visibility = when {
                it.toString().isEmpty() -> View.INVISIBLE
                else -> View.VISIBLE
            }
        }
        holder.itemView.btnDone.setOnClickListener {
            holder.itemView.etName.clearFocus()
            childListener.saveChild(holder.itemView.etName.text.toString(), child.avatar)

            holder.itemView.etName.setText("")
            holder.itemView.ivUnknownAvatar.setImageResource(R.drawable.ic_unknown_kid)
        }
    }

    private fun bindChild(holder: ChildHolder, position: Int) {
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
    class AddChildHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}