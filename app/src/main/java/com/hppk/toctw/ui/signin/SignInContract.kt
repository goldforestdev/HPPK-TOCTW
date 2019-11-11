package com.hppk.toctw.ui.signin

interface SignInContract {
    interface View {
        fun onSignInDone()
    }

    interface Presenter {
        fun saveMe()
        fun unsubscribe()
    }
}