package com.hppk.toctw.ui.booth.details.addrating

import com.hppk.toctw.data.model.User

interface AddRatingContract {
    interface View {
        fun onMeLoaded(me: User)
    }

    interface Presenter {
        fun unsubscribe()
        fun getMe()
    }
}