package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Favorites
import io.reactivex.Completable
import io.reactivex.Single

interface FavoritesDao {
    fun save(vararg favoriteId: Favorites): Completable
    fun getAll() : Single<List<Favorites>>

}