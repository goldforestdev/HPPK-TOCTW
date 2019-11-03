package com.hppk.toctw.auth

import android.util.Log
import com.hppk.toctw.data.model.User
import com.hppk.toctw.data.repository.UserRepository
import com.hppk.toctw.data.source.impl.FirestoreUserDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserPresenter(
    private val view: UserContract.View,
    private val boothRepository: UserRepository = UserRepository(remoteUserDao = FirestoreUserDao()),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()

) : UserContract.Presenter {
    private val TAG = UserPresenter::class.java.simpleName

    override fun addUser(user: User) {
        disposable.add(
            boothRepository.save(user)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.e(TAG, "[TOCTW] addUser : " + user.email)
                    view.onAddUserSuccess(user)
                }, { t->
                    Log.e(TAG, "[TOCTW] addUser - failed: ${t.message}", t)
                    view.onAddUserError()
                })
        )
    }

    override fun findUser(id: String) {
        disposable.add(
            boothRepository.get(id)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.e(TAG, "[TOCTW] findUser : " + it.email)
                    view.onFindUserSuccess(it)
                }, { t->
                    Log.e(TAG, "[TOCTW] findUser - failed: ${t.message}", t)
                    view.onFindUserError()
                })
        )
    }

    override fun unsubscribe() {
        disposable.clear()
    }

}
