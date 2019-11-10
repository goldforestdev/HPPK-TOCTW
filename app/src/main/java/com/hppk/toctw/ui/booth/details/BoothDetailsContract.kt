package com.hppk.toctw.ui.booth.details

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review

interface BoothDetailsContract {
    interface View {
        fun onReviewsLoaded(reviews: List<Review>)
        fun onEmptyReviewsLoaded()
    }

    interface Presenter {
        fun unsubscribe()
        fun getReviews(booth: Booth)
    }
}