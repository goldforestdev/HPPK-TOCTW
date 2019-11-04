package com.hppk.toctw.ui.home

import com.hppk.toctw.data.model.Notice


interface NoticeContract {
    interface View {
        fun onError()
        fun onNoticeListLoaded(noticeDataList: List<Notice>)
        fun onAddNoticeSuccess(notice: Notice)
    }

    interface Presenter {
        fun addNotice(notice: Notice)
        fun loadCollection()
        fun unsubscribe()
    }
}