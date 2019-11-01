package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.source.BoothDao

class BoothRepository(
    private val localBoothDao: BoothDao? = null,
    private val remoteBoothDao: BoothDao
) {

    fun save(booth: Booth) = remoteBoothDao.save(booth)

    fun get(id: String) = remoteBoothDao.get(id)

    fun getDataList() = remoteBoothDao.getDataList()

    fun getStampBoothList() = remoteBoothDao.getStampBoothList()

}