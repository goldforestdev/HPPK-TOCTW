package com.hppk.toctw.ui.booth.details.addrating

import android.util.Log
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review
import com.hppk.toctw.data.model.User
import com.hppk.toctw.data.repository.ReviewRepository
import com.hppk.toctw.data.repository.UserRepository
import com.hppk.toctw.data.source.impl.FirestoreReviewDao
import com.hppk.toctw.data.source.impl.FirestoreUserDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddRatingPresenter(
    private val view: AddRatingContract.View,
    private val userRepo: UserRepository = UserRepository(remoteUserDao = FirestoreUserDao()),
    private val reviewRepo: ReviewRepository = ReviewRepository(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : AddRatingContract.Presenter {

    private val TAG = AddRatingPresenter::class.java.simpleName

    private lateinit var me: User

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun getMe() {
        disposable.add(
            userRepo.getMe()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    me = it
                    view.onMeLoaded(it)
                }, { t ->
                    Log.e(TAG, "[TOCTW] getMe - failed: ${t.message}", t)
                })
        )
    }

    override fun saveReview(booth: Booth, rating: Float, review: String) {
        disposable.add(
            reviewRepo.save(booth, Review(me.id, me.name, me.profile, rating, review))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onReviewSaved()
                }, {t ->
                    Log.e(TAG, "[TOCTW] saveReview - failed: ${t.message}", t)
                })
        )
    }

}