package com.hppk.toctw.data.source

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review
import io.reactivex.Completable
import io.reactivex.Single

interface ReviewDao {

    fun save(booth: Booth, review: Review): Completable
    fun getReviews(booth: Booth): Single<List<Review>>
}