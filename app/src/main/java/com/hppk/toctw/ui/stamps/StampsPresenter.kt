package com.hppk.toctw.ui.stamps

import com.hppk.toctw.data.model.StampBooth
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
        // TODO: stamp repository 구현 후 작업할 것
        val sampleData = listOf(
            StampBooth("1", "booth 1"),
            StampBooth("2", "booth 2"),
            StampBooth("3", "booth 3"),
            StampBooth("4", "booth 4"),
            StampBooth("5", "booth 5"),
            StampBooth("6", "booth 6"),
            StampBooth("7", "booth 7"),
            StampBooth("8", "booth 8"),
            StampBooth("9", "booth 9")
        )

        view.onStampsLoaded(sampleData)

        // TODO: 모든 stamp를 획득했을 때의 시나리오
    }
}