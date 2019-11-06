package com.hppk.toctw.ui.stamps

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Stamp

interface StampsContract{
    interface View {
        fun onStampsLoaded(stamps: List<Stamp>)
    }

    interface Presenter {
        fun unsubscribe()
        fun getStamps(child: Child)
    }
}