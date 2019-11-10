package com.hppk.toctw.ui.booth.details

import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Role
import com.hppk.toctw.data.model.User
import com.hppk.toctw.data.repository.ReviewRepository
import com.hppk.toctw.data.repository.UserRepository
import com.hppk.toctw.data.source.impl.FirestoreReviewDao
import com.hppk.toctw.data.source.impl.FirestoreUserDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BoothDetailsPresenter(
    private val view: BoothDetailsContract.View,
    private val userRepo: UserRepository = UserRepository(remoteUserDao = FirestoreUserDao()),
    private val reviewRepo: ReviewRepository = ReviewRepository(FirestoreReviewDao()),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
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
                    view.showSignInButton(View.GONE)
                }, {
                    view.showSignInButton(View.VISIBLE)
                })
        )
    }

    override fun saveMe() {
        auth.currentUser?.let {
            val me = User(it.email ?: "", it.email ?: "", it.displayName ?: "", it.photoUrl.toString(), Role.GENERAL)
            userRepo.save(me)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.showSignInButton(View.GONE)
                }, { t ->
                    Log.e(TAG, "[TOCTW] saveMe - failed: ${t.message}", t)
                })
        }
    }

    override fun getReviews(booth: Booth) {
        disposable.add(
            reviewRepo.getReviews(booth)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
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