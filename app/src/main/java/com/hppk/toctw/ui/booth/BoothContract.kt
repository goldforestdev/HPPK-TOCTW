package com.hppk.toctw.ui.booth

import com.hppk.toctw.data.model.Booth


interface BoothContract {
    interface View {
        fun onError(errTitle: Int, errMsg: Int)
        fun onBoothListLoaded(boothDataList: List<Booth>)
        fun showWaitingView(show: Boolean)

    }

    interface Presenter {
        fun loadDocumentData()
        fun loadCollection()
        fun unsubscribe()
    }
}