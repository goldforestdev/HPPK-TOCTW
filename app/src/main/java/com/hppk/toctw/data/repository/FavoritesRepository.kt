package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Favorites
import com.hppk.toctw.data.source.FavoritesDao
import io.reactivex.Single

class FavoritesRepository(
    private val localFavoritesDao: FavoritesDao,
    private val remoteFavoritesDao: FavoritesDao? = null
)  {

    fun save(favoriteId: Favorites) = localFavoritesDao.save(favoriteId)


    fun getAll(): Single<List<Favorites>> = localFavoritesDao.getAll()
}