package com.hppk.toctw.ui.booth.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Review
import kotlinx.android.synthetic.main.item_review.view.*


class ReviewsAdapter(
    val reviews: MutableList<Review> = mutableListOf()
) : RecyclerView.Adapter<ReviewsAdapter.ReviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder = ReviewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_review,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        val review = reviews[position]

        with(holder.itemView) {
            tvUserName.text = review.userName
            tvReview.text = review.review
            ratingBar.rating = review.rating
        }
    }


    class ReviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}