package com.hppk.toctw.ui.booth

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Favorites


interface BoothContract {
    interface View {
        fun onError(errTitle: Int, errMsg: Int)
        fun onBoothListLoaded(boothDataList: List<Booth>, favorites: List<Favorites>)
        fun showWaitingView(show: Boolean)
        fun onUpdateBoothInfoSuccess()
    }

    interface Presenter {
        fun loadDocumentData()
        fun loadCollection()
        fun updateBoothInfo(booth: Booth)
        fun unsubscribe()
    }
}