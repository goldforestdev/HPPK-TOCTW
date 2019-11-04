package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.model.StampBooth
import com.hppk.toctw.data.source.local.LocalChildStampDao
import com.hppk.toctw.data.source.local.LocalStampBoothDao

class StampRepository(
    private val localStampBoothDao: LocalStampBoothDao,
    private val localChildStampDao: LocalChildStampDao
) {

    fun save(stampBooth: StampBooth) = localStampBoothDao.save(stampBooth)

    fun save(childStampJoin: ChildStampJoin) = localChildStampDao.save(childStampJoin)

    fun getStamps(name: String) = localChildStampDao.getStampsForChild(name)

}