package com.hppk.toctw.auth

import com.hppk.toctw.data.model.User

interface UserContract {
    interface View {
        fun onFindUserError()
        fun onFindUserSuccess(user: User)
        fun onAddUserError()
        fun onAddUserSuccess(user: User)
    }

    interface Presenter {
        fun addUser()
        fun findUser()
        fun unsubscribe()
    }
}
