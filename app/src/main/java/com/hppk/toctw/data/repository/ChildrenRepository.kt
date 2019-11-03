package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.source.ChildrenDao

class ChildrenRepository (
    private val localChildrenDao: ChildrenDao,
    private val remoteChildrenDao: ChildrenDao? = null
) {
    fun save(child: Child) = localChildrenDao.save(child)

    fun getAll() = localChildrenDao.getAll()

    fun delete(child: Child) = localChildrenDao.delete(child)
}