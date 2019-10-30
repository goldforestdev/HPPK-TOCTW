package com.hppk.toctw.ui.splash


interface SplashContract {
    interface View {
        fun onError(errTitle: Int, errMsg: Int)
    }

    interface Presenter {
        fun unsubscribe()
    }
}