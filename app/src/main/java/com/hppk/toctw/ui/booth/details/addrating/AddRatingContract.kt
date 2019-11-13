package com.hppk.toctw.ui.booth.details.addrating

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.User

interface AddRatingContract {
    interface View {
        fun onMeLoaded(me: User)
        fun onReviewSaved()
    }

    interface Presenter {
        fun unsubscribe()
        fun getMe()
        fun saveReview(booth: Booth, rating: Float, review: String)
        fun checkAchievement()
    }
}