package com.hppk.toctw.auth

import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.data.model.Role
import com.hppk.toctw.data.model.User

object AppAuth {
    private val TAG = AppAuth::class.java.simpleName
    private lateinit var auth: FirebaseAuth
    private var mUser: User? = null

    fun isFirebaseLoginUser(): Boolean {
        auth = FirebaseAuth.getInstance()
        return auth.currentUser != null
    }

    fun setUser(user: User?) {
        mUser = if (user != null) {
            User(user.id, user.email, user.name, user.role)
        } else {
            null
        }
    }

    fun getUser(): User? = mUser

    fun isAdmin(): Boolean = mUser?.role?.equals(Role.ADMIN) ?: false

    fun isStaff(): Boolean = mUser?.role?.equals(Role.STAFF) ?: false
}