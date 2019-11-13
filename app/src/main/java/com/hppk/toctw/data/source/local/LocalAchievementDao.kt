package com.hppk.toctw.data.source.local

import androidx.room.*
import com.hppk.toctw.data.model.Achievement
import com.hppk.toctw.data.source.AchievementDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface LocalAchievementDao : AchievementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun save(achievement: Achievement): Completable

    @Query("SELECT * FROM achievement")
    override fun getAll(): Single<List<Achievement>>
}