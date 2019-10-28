package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.User
import com.hppk.toctw.data.source.UserDao

class UserRepository(
    private val localUserDao: UserDao? = null, // 혹시 사용자를 로컬 캐시해야 하는 경우를 위해 남겨 둡니다.
    private val remoteUserDao: UserDao
) {

    fun save(user: User) = remoteUserDao.save(user)

    fun get(id: String) = remoteUserDao.get(id)

}