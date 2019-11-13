package com.hppk.toctw.ui.settings

import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.auth.AppAuth

class SettingsPresenter(
    private val view: SettingsContract.View,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : SettingsContract.Presenter {

    override fun isSignedIn() {
        view.showSignOutButton(auth.currentUser != null, AppAuth.isStaff)
    }

    override fun signOut() {
        auth.signOut()
        view.showSignOutButton(show = false, isStaff = false)
        AppAuth.setUser(null)
    }
}