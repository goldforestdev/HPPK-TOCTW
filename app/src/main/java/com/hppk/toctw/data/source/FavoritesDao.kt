package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Favorites
import com.hppk.toctw.data.model.Stamp
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface FavoritesDao {
    fun save(vararg favorites: Favorites): Completable
    fun getFavoritesBooths() : Single<List<Favorites>>
    fun getBoothFavoriteInfo(id : String) : Single<Favorites>
    fun getAll() : Single<List<Favorites>>

}