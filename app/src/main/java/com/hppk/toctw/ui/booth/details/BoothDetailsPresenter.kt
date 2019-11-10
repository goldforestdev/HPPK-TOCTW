package com.hppk.toctw.ui.booth.details

import android.util.Log
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.repository.ReviewRepository
import com.hppk.toctw.data.source.impl.FirestoreReviewDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BoothDetailsPresenter(
    private val view: BoothDetailsContract.View,
    private val reviewRepo: ReviewRepository = ReviewRepository(FirestoreReviewDao()),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : BoothDetailsContract.Presenter {

    private val TAG = BoothDetailsPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun getReviews(booth: Booth) {
        disposable.add(
            reviewRepo.getReviews(booth)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.d(TAG, "[TOCTW] getReviews - done: ${it.size}")
                    if (it.isNullOrEmpty()) {
                        view.onEmptyReviewsLoaded()
                    } else {
                        view.onReviewsLoaded(it)
                    }
                }, { t ->
                    Log.e(TAG, "[TOCTW] getReviews - failed: ${t.message}", t)
                })
        )
    }

}