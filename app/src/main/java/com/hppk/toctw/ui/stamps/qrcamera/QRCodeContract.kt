package com.hppk.toctw.ui.stamps.qrcamera

import com.hppk.toctw.data.model.Child

interface QRCodeContract {
    interface View {
        fun onStampSaved()
    }

    interface Presenter {
        fun unsubscribe()
        fun saveStamp(child: Child, boothId: String)
    }
}