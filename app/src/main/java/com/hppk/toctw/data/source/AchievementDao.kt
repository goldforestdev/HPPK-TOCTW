package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Achievement
import io.reactivex.Completable
import io.reactivex.Single

interface AchievementDao {
    fun save(achievement: Achievement): Completable
    fun getAll(): Single<List<Achievement>>
}