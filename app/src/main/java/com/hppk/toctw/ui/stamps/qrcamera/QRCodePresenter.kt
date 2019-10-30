package com.hppk.toctw.ui.stamps.qrcamera

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QRCodePresenter (
    private val view: QRCodeContract.View,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): QRCodeContract.Presenter {

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun saveStamp(boothId: String) {
        // TODO: stamp repository 구현 후 작업할 것
        view.onStampSaved()
    }
}