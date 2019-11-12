package com.hppk.toctw.data.repository

import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review
import com.hppk.toctw.data.source.impl.FirestoreReviewDao
import io.reactivex.Completable
import io.reactivex.Single

class ReviewRepository(
    private val reviewDao: FirestoreReviewDao = FirestoreReviewDao()
) {
    fun save(booth: Booth, review: Review): Completable = reviewDao.save(booth, review)

    fun getLittleReviews(booth: Booth): Single<List<Review>> = reviewDao.getLittleReviews(booth)

    fun getReviews(booth: Booth): Single<List<Review>> = reviewDao.getReviews(booth)
}