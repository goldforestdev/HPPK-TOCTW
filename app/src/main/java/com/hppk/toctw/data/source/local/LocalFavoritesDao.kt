package com.hppk.toctw.data.source.local

import androidx.room.*
import com.hppk.toctw.data.model.Favorites
import com.hppk.toctw.data.source.FavoritesDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocalFavoritesDao : FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun save(vararg favoriteId: Favorites): Completable

    @Query("SELECT * FROM favorites")
    override fun getAll() : Single<List<Favorites>>

    @Delete
    override fun delete(vararg favoriteId: Favorites): Completable

}