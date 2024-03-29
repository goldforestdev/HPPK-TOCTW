package com.hppk.toctw.ui.booth.details

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review

interface BoothDetailsContract {
    interface View {
        fun onReviewsLoaded(reviews: List<Review>)
        fun onEmptyReviewsLoaded()
        fun showSignInButton(visible: Int)
        fun showMoreReviewButton(show: Boolean)
    }

    interface Presenter {
        fun unsubscribe()
        fun isSignedIn()
        fun getReviews(booth: Booth)
        fun getReviewsMore(booth: Booth)
    }
}