package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Child
import io.reactivex.Completable
import io.reactivex.Single

interface ChildrenDao {
    fun save(child: Child): Completable
    fun getAll(): Single<List<Child>>
    fun delete(child: Child): Completable

}