package com.hppk.toctw.ui.booth.details.reviews

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review

interface ReviewsContract {
    interface View {
        fun onReviewsLoaded(reviews: List<Review>)
        fun showProgressBar(visibility: Int)
    }

    interface Presenter {
        fun getAllReviews(booth: Booth)
    }
}