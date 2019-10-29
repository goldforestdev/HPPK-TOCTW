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
        // TODO: stamp repository 구현 후 작업할 것
        val sampleData = listOf(
            Stamp("1", "booth 1", true),
            Stamp("2", "booth 2", false),
            Stamp("3", "booth 3", true),
            Stamp("4", "booth 4", false),
            Stamp("5", "booth 5", false),
            Stamp("6", "booth 6", false),
            Stamp("7", "booth 7", true),
            Stamp("8", "booth 8", false),
            Stamp("9", "booth 9", false)
        )

        view.onStampsLoaded(sampleData)

        // TODO: 모든 stamp를 획득했을 때의 시나리오
    }
}