package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Achievement
import com.hppk.toctw.data.source.AchievementDao
import io.reactivex.Completable
import io.reactivex.Single

class AchievementRepository(
    private val localAchievementDao: AchievementDao
) {

    fun save(achievement: Achievement): Completable {
        return localAchievementDao.save(achievement)
    }

    fun getAll(): Single<List<Achievement>> {
        return localAchievementDao.getAll()
    }

}