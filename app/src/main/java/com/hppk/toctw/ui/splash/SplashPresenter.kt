package com.hppk.toctw.ui.splash

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SplashPresenter (
    private val view: SplashContract.View,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): SplashContract.Presenter {

    private val TAG = SplashPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

}