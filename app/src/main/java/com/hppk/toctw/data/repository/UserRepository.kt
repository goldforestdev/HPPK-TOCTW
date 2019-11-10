package com.hppk.toctw.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.data.UserNotExistException
import com.hppk.toctw.data.model.User
import com.hppk.toctw.data.source.UserDao
import io.reactivex.Completable
import io.reactivex.Single

class UserRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val remoteUserDao: UserDao
) {

    private var cachedMe: User? = null

    fun getMe(): Single<User> {
        if (cachedMe == null) {
            if (auth.currentUser == null) {
                return Single.error(UserNotExistException("me"))
            } else {
                auth.currentUser?.email?.let { email ->
                    return get(email).doOnSuccess { me -> cachedMe = me }
                } ?: return Single.error(UserNotExistException("me"))
            }
        } else {
            return Single.just(cachedMe)
        }
    }

    fun save(user: User): Completable {
        cachedMe = user
        return remoteUserDao.save(user)
    }

    fun get(id: String): Single<User> = remoteUserDao.get(id)

}