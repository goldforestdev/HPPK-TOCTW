package com.hppk.toctw.ui.booth.details.reviews

import android.util.Log
import android.view.View
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.repository.ReviewRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReviewsPresenter(
    private val view: ReviewsContract.View,
    private val reviewRepo: ReviewRepository = ReviewRepository(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : ReviewsContract.Presenter {

    private val TAG = ReviewsPresenter::class.java.simpleName

    override fun getAllReviews(booth: Booth) {
        disposable.add(
            reviewRepo.getReviews(booth)
                .doOnSubscribe { view.showProgressBar(View.VISIBLE) }
                .doOnSuccess{ view.showProgressBar(View.GONE) }
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onReviewsLoaded(it)
                }, {t ->
                    Log.e(TAG, "[TOCTW] getAllReviews - failed: ${t.message}", t)
                })
        )
    }

}