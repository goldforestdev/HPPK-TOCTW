package com.hppk.toctw.auth

import android.text.TextUtils
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
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

    override fun addUser() {
        val id = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        if(TextUtils.isEmpty(id)) {
            view.onAddUserError()
            return
        }

        val email = FirebaseAuth.getInstance().currentUser?.email ?: ""
        val displayName = FirebaseAuth.getInstance().currentUser?.displayName ?: ""
        val newUser = User(id, email, displayName)
        disposable.add(
            boothRepository.save(newUser)
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

    override fun findUser(id: String) {
        disposable.add(
            boothRepository.get(id)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.e(TAG, "[TOCTW] findUser : " + it.email)
                    AppAuth.setUser(it)
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
