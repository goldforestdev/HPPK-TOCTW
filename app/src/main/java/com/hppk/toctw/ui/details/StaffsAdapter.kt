package com.hppk.toctw.ui.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Staff
import kotlinx.android.synthetic.main.item_staff.view.*


class StaffsAdapter(
    val staffs : MutableList<Staff> = mutableListOf(),
    private var context : Context? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return StaffsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent, false))
    }


    override fun getItemCount(): Int  = staffs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is StaffsHolder) {
            with (holder) {
                tvName.text = staffs[position].name
            }
        }
    }


    class StaffsHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName : TextView = itemView.tvName
    }
}