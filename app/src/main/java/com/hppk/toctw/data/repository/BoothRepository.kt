package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Busy
import com.hppk.toctw.data.source.BoothDao
import io.reactivex.Single

class BoothRepository(
    private val localBoothDao: BoothDao? = null,
    private val remoteBoothDao: BoothDao
) {

    fun save(booth: Booth) = remoteBoothDao.save(booth)

    fun get(id: String) = remoteBoothDao.get(id)

    fun getDataList() = remoteBoothDao.getBoothList()

    fun updateBoothInfo(booth: Booth) = remoteBoothDao.updateBooth(booth)

    fun getBoothBusyStatusList(busyStatus : List<Busy>)  = remoteBoothDao.getBoothBusyStatusList(busyStatus)

}