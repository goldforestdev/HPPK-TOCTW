package com.hppk.toctw.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.auth.AppAuth
import com.hppk.toctw.data.model.Notice
import kotlinx.android.synthetic.main.item_home_header.view.*
import kotlinx.android.synthetic.main.item_home_notice_list.view.*


class HomeAdapter(
    val notices: MutableList<Notice> = mutableListOf(),
    private var context: Context? = null,
    private val clickLister: ClickLister
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        if (viewType == TYPE_HEADER) {
            return HomeHeaderHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home_header,
                    parent,
                    false
                )
            )
        } else {
            return NoticeHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home_notice_list,
                    parent,
                    false
                )
            )
        }
    }

    interface ClickLister {
        fun onAddNoticeClick()
        fun onShowBoothClick()
        fun onShowYouTube()
    }

    override fun getItemCount(): Int = notices.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NoticeHolder) {
            with(holder) {
                title.text = notices[position - 1].title
                body.text = notices[position - 1].body
                time.text = notices[position - 1].timeStamp.toDate().toString()
            }
        } else if (holder is HomeHeaderHolder) {
            with(holder) {
                if (AppAuth.isAdmin) {
                    addNotice.visibility = View.VISIBLE
                } else {
                    addNotice.visibility = View.GONE
                }

                addNotice.setOnClickListener {
                    clickLister.onAddNoticeClick()
                }

                showBooth.setOnClickListener {
                    clickLister.onShowBoothClick()
                }

                showYoutube.setOnClickListener {
                    clickLister.onShowYouTube()
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return TYPE_ITEM
    }

    class HomeHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val addNotice: TextView = itemView.tv_add_notice
        val showBooth: TextView = itemView.tv_show_booth
        val showYoutube: CardView = itemView.cardYouTube
    }

    class NoticeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tv_item_notice_title
        val body: TextView = itemView.tv_item_notice_body
        val time: TextView = itemView.tv_item_notice_time
    }

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

}