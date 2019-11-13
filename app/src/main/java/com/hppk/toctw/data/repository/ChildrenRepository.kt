package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.source.ChildrenDao
import com.hppk.toctw.data.source.local.LocalChildStampDao
import io.reactivex.Completable

class ChildrenRepository (
    private val localChildrenDao: ChildrenDao,
    private val localChildStampDao: LocalChildStampDao,
    private val remoteChildrenDao: ChildrenDao? = null
) {
    fun save(child: Child) = localChildrenDao.save(child)

    fun getAll() = localChildrenDao.getAll()

    fun delete(child: Child): Completable {
        return localChildStampDao.delete(child.name)
            .concatWith(localChildrenDao.delete(child))
    }
}