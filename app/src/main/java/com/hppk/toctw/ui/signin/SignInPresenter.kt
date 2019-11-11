package com.hppk.toctw.ui.signin

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.hppk.toctw.data.model.Role
import com.hppk.toctw.data.model.User
import com.hppk.toctw.data.repository.UserRepository
import com.hppk.toctw.data.source.impl.FirestoreUserDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignInPresenter (
    private val view: SignInContract.View,
    private val userRepo: UserRepository = UserRepository(remoteUserDao = FirestoreUserDao()),
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): SignInContract.Presenter {
    private val TAG = SignInPresenter::class.java.simpleName

    override fun saveMe() {
        auth.currentUser?.let {
            val me = User(it.uid, it.email ?: "", it.displayName ?: "", it.photoUrl.toString(), Role.GENERAL)
            disposable.add(
                userRepo.save(me)
                    .subscribeOn(ioScheduler)
                    .observeOn(uiScheduler)
                    .subscribe({
                        view.onSignInDone()
                    }, { t ->
                        Log.e(TAG, "[TOCTW] saveMe - failed: ${t.message}", t)
                    })
            )
        }
    }

    override fun unsubscribe() {
        disposable.clear()
    }

}