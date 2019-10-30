package com.hppk.toctw.ui.booth

import android.util.Log
import com.hppk.toctw.data.repository.BoothRepository
import com.hppk.toctw.data.source.impl.FirestoreBoothDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BoothPresenter (
    private val view: BoothContract.View,
    private val boothRepository: BoothRepository = BoothRepository(remoteBoothDao =  FirestoreBoothDao()),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): BoothContract.Presenter {

    private val TAG = BoothPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun loadDocumentData() {
        disposable.add(
            boothRepository.get("openning")
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.e(TAG, "[TOCTW] loadData : " + it.location)
                }, { t->
                    Log.e(TAG, "[TOCTW] loadData - failed: ${t.message}", t)
                })
        )
    }

    override fun loadCollection() {
        disposable.add(
            boothRepository.getDataList()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onBoothListLoaded(it)
                }, { t->
                    Log.e(TAG, "[TOCTW] loadCollection - failed: ${t.message}", t)
                })
        )
    }



}