package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserDao {
    fun save(user: User): Completable
    fun get(id: String): Single<User>

}