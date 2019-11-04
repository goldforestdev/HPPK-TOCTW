package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Notice
import com.hppk.toctw.data.source.NoticeDao

class NoticeRepository(
    private val localNoticeDao: NoticeDao? = null,
    private val remoteNoticeDao: NoticeDao
) {

    fun save(notice: Notice) = remoteNoticeDao.save(notice)

    fun getDataList() = remoteNoticeDao.getDataList()

}