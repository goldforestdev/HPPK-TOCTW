package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Favorites
import com.hppk.toctw.data.source.FavoritesDao
import io.reactivex.Single

class FavoritesRepository(
    private val localFavoritesDao: FavoritesDao,
    private val remoteFavoritesDao: FavoritesDao? = null
)  {

    fun save(favorites: Favorites) = localFavoritesDao.save(favorites)

    fun getFavoritesBooths()  = localFavoritesDao.getFavoritesBooths()

    fun getBoothFavoriteInfo(id : String)  = localFavoritesDao.getBoothFavoriteInfo(id)

    fun getAll(): Single<List<Favorites>> = localFavoritesDao.getFavoritesBooths()
}