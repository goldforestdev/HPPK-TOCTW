package com.hppk.toctw.auth

import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.data.model.Role
import com.hppk.toctw.data.model.User

object AppAuth {
    private val TAG = AppAuth::class.java.simpleName
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mUser: User? = null

    fun isFirebaseLoginUser(): Boolean = auth.currentUser != null

    fun getFirebaseLoginUserId(): String = auth.currentUser?.uid ?: ""

    fun getUserFromFirebaseAuth(): User? {
        if (auth.currentUser == null) return null

        val id = auth.currentUser?.uid ?: ""
        if (id.isBlank()) return null

        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val displayName = FirebaseAuth.getInstance().currentUser?.displayName ?: ""
        return User(id, email, displayName)
    }

    fun setUser(user: User?) {
        mUser = user
    }

    fun getUser(): User? = mUser

    fun isAdmin(): Boolean = mUser?.role?.equals(Role.ADMIN) ?: false

    fun isStaff(): Boolean = mUser?.role?.equals(Role.STAFF) ?: false
}