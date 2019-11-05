package com.hppk.toctw.ui.stamps.qrcamera

import android.util.Log
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.ChildStampJoin
import com.hppk.toctw.data.repository.StampRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QRCodePresenter(
    private val view: QRCodeContract.View,
    private val stampRepo: StampRepository,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : QRCodeContract.Presenter {

    private val TAG = QRCodePresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun saveStamp(child: Child, boothId: String) {
        disposable.add(
            stampRepo.saveChildStampJoin(ChildStampJoin(child.name, boothId))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onStampSaved()
                }, {t ->
                    Log.e(TAG, "[TOCTW] saveStamp - failed: ${t.message}", t)
                })
        )
    }
}