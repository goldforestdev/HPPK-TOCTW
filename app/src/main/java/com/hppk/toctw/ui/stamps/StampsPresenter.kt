package com.hppk.toctw.ui.stamps

import android.util.Log
import com.hppk.toctw.R
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.repository.StampRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StampsPresenter(
    private val view: StampsContract.View,
    private val stampRepository: StampRepository,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : StampsContract.Presenter {

    private val TAG = StampsPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun getStamps(child: Child) {
        disposable.add(
            stampRepository.getStamps(child.name)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ stamps ->
                    val stampGrp = stamps.map { StampFlipWrapper(it) }.groupBy { it.stamp.isDone }
                    val stampsInProgress = stampGrp[false]
                    val stampsComplete = stampGrp[true]

                    val stampList = mutableListOf<Any>()
                    if (!stampsInProgress.isNullOrEmpty()) {
                        stampList.add(R.string.in_progress)
                        stampList.addAll(stampsInProgress)
                        view.showQRButton(true)
                    } else {
                        view.showQRButton(false)
                    }

                    if (!stampsComplete.isNullOrEmpty()) {
                        stampList.add(R.string.complete)
                        stampList.addAll(stampsComplete)
                    }

                    view.onStampsLoaded(stampList)
                }, { t ->
                    Log.e(TAG, "[TOCTW] getStamps - failed: ${t.message}", t)
                })
        )
    }
}