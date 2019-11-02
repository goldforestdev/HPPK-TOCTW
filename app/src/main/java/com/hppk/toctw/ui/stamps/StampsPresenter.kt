package com.hppk.toctw.ui.stamps

import android.util.Log
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Busy
import com.hppk.toctw.data.model.Staff
import com.hppk.toctw.data.model.StampBooth
import com.hppk.toctw.data.repository.BoothRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StampsPresenter(
    private val view: StampsContract.View,
    private val boothRepository: BoothRepository,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : StampsContract.Presenter {

    private val TAG = StampsPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun getStamps() {
        disposable.add(
            boothRepository.getStampBoothList()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({ booths ->
                    Log.d(TAG, "[TOCTW] getStamps - booths: $booths")
                    view.onStampsLoaded(booths.map(::StampBooth))
                }, { t -> Log.e(TAG, "[TOCTW] getStamps - failed: ${t.message}", t) })
        )

        // TODO: 모든 stamp를 획득했을 때의 시나리오

    }
}