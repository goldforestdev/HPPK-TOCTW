package com.hppk.toctw.ui.stamps

import com.hppk.toctw.data.model.StampBooth

interface StampsContract{
    interface View {
        fun onStampsLoaded(stamps: List<StampBooth>)
    }

    interface Presenter {
        fun unsubscribe()
        fun getStamps()
    }
}