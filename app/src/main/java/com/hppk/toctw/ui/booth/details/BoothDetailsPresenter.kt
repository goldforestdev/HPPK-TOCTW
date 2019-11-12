package com.hppk.toctw.ui.booth.details

import android.util.Log
import android.view.View
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.repository.ReviewRepository
import com.hppk.toctw.data.repository.UserRepository
import com.hppk.toctw.data.source.impl.FirestoreReviewDao
import com.hppk.toctw.data.source.impl.FirestoreUserDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


private const val MAXIMUM_REVIEW_NUM = 3

class BoothDetailsPresenter(
    private val view: BoothDetailsContract.View,
    private val userRepo: UserRepository = UserRepository(remoteUserDao = FirestoreUserDao()),
    private val reviewRepo: ReviewRepository = ReviewRepository(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : BoothDetailsContract.Presenter {
    private val TAG = BoothDetailsPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun isSignedIn() {
        disposable.add(
            userRepo.getMe()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.d(TAG, "[TOCTW] isSignedIn - me: $it")
                    view.showSignInButton(View.GONE)
                }, {
                    view.showSignInButton(View.VISIBLE)
                })
        )
    }

    override fun getReviews(booth: Booth) {
        disposable.add(
            reviewRepo.getLittleReviews(booth)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ reviews ->
                    if (reviews.isNullOrEmpty()) {
                        view.onEmptyReviewsLoaded()
                    } else {
                        view.onReviewsLoaded(reviews.take(MAXIMUM_REVIEW_NUM))
                        view.showMoreReviewButton(reviews.size > MAXIMUM_REVIEW_NUM)
                    }
                }, { t ->
                    Log.e(TAG, "[TOCTW] getLittleReviews - failed: ${t.message}", t)
                })
        )
    }

    override fun getReviewsMore(booth: Booth) {

    }

}