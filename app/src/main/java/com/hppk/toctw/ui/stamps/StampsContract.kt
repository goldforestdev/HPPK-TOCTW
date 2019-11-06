package com.hppk.toctw.ui.stamps

import com.hppk.toctw.data.model.Child

interface StampsContract{
    interface View {
        fun onStampsLoaded(stamps: List<Any>)
        fun hideQRButton(hide: Boolean)
    }

    interface Presenter {
        fun unsubscribe()
        fun getStamps(child: Child)
    }
}