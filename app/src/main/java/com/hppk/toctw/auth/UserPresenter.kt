package com.hppk.toctw.auth

import android.util.Log
import com.hppk.toctw.data.model.Role
import com.hppk.toctw.data.repository.UserRepository
import com.hppk.toctw.data.source.impl.FirestoreUserDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserPresenter(
    private val view: UserContract.View,
    private val userRepository: UserRepository = UserRepository(remoteUserDao = FirestoreUserDao()),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()

) : UserContract.Presenter {
    private val TAG = UserPresenter::class.java.simpleName

    override fun addUser() {
        val newUser = AppAuth.getUserFromFirebaseAuth()
        if(newUser == null)  {
            view.onAddUserError()
            return
        }
        disposable.add(
            userRepository.save(newUser)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.e(TAG, "[TOCTW] addUser : " + newUser.email)
                    AppAuth.setUser(newUser)
                    view.onAddUserSuccess(newUser)
                }, { t->
                    Log.e(TAG, "[TOCTW] addUser - failed: ${t.message}", t)
                    view.onAddUserError()
                })
        )
    }

    override fun findUser() {
        if(AppAuth.getFirebaseLoginUserId().isBlank())  {
            AppAuth.setUser(null)
            view.onFindUserError()
            return
        }

        disposable.add(
            userRepository.get(AppAuth.getFirebaseLoginUserId())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ user ->
                    Log.e(TAG, "[TOCTW] findUser : " + user.email)
                    if (user.role == Role.GENERAL) {
                        user.role = Role.STAFF
                    }
                    userRepository.save(user)
                        .subscribeOn(ioScheduler)
                        .subscribe()

                    AppAuth.setUser(user)
                    view.onFindUserSuccess(user)
                }, { t->
                    Log.e(TAG, "[TOCTW] findUser - failed: ${t.message}", t)
                    AppAuth.setUser(null)
                    view.onFindUserError()
                })
        )
    }

    override fun unsubscribe() {
        disposable.clear()
    }

}
