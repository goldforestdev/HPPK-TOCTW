package com.hppk.toctw.ui.settings

interface SettingsContract {
    interface View {
        fun showSignOutButton(show: Boolean, isStaff : Boolean)
    }

    interface Presenter {
        fun isSignedIn()
        fun signOut()
    }
}