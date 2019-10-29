package com.hppk.toctw.ui.stamps.qrcamera

interface QRCodeContract {
    interface View {
        fun onStampSaved()
    }

    interface Presenter {
        fun unsubscribe()
        fun saveStamp(boothId: String)
    }
}