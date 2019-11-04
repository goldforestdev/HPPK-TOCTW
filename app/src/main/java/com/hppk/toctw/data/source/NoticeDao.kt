package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Notice
import io.reactivex.Completable
import io.reactivex.Single

interface NoticeDao {
    fun save(notice: Notice): Completable
    fun getDataList(): Single<List<Notice>>
}
