package com.hppk.toctw.ui.stamps

import android.util.Log
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
                    Log.d(TAG, "[TOCTW] getStamps - stamps: ${stamps.size}")
                    view.onStampsLoaded(stamps)
                }, { t ->
                    Log.e(TAG, "[TOCTW] getStamps - failed: ${t.message}", t)
                })
        )

        // TODO: 모든 stamp를 획득했을 때의 시나리오

    }
}