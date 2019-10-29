package com.hppk.toctw.ui.stamps

import com.hppk.toctw.data.model.Stamp
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StampsPresenter(
    private val view: StampsContract.View,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : StampsContract.Presenter {

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun getStamps() {
        val sampleData = listOf(
            Stamp("1", "booth 1", true),
            Stamp("2", "booth 2", false),
            Stamp("3", "booth 3", true),
            Stamp("4", "booth 4", false),
            Stamp("5", "booth 5", false)
        )
        view.onStampsLoaded(sampleData)
    }
}