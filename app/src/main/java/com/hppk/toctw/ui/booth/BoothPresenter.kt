package com.hppk.toctw.ui.booth

import android.util.Log
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.repository.BoothRepository
import com.hppk.toctw.data.repository.FavoritesRepository
import com.hppk.toctw.data.source.FavoritesDao
import com.hppk.toctw.data.source.impl.FirestoreBoothDao
import com.hppk.toctw.data.source.local.LocalFavoritesDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BoothPresenter (
    private val view: BoothContract.View,
    private val boothRepository: BoothRepository = BoothRepository(remoteBoothDao =  FirestoreBoothDao()),
    private val favoritesRepository: FavoritesRepository,
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
            boothRepository.getBootList()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe{view.showWaitingView(true)}
                .doOnError { view.showWaitingView(false) }
                .doOnSuccess { view.showWaitingView(false) }
                .subscribe({
                    view.onBoothListLoaded(it)
                    updateFavoritesData(it)
                }, { t->
                    Log.e(TAG, "[TOCTW] loadCollection - failed: ${t.message}", t)
                })
        )
    }


    private fun updateFavoritesData(boothList : List<Booth>) {
        if(boothList.isNotEmpty()) {
            for (data in boothList) {
                disposable.add(
                    favoritesRepository.getBoothFavoriteInfo(data.id)
                        .subscribeOn(ioScheduler)
                        .observeOn(uiScheduler)
                        .doOnSubscribe{view.showWaitingView(true)}
                        .doOnError { view.showWaitingView(false) }
                        .doOnSuccess { view.showWaitingView(false) }
                        .subscribe({
                            Log.e(TAG, "[TOCTW] updateFavoritesData - success:")

                        }, { t->
                            Log.e(TAG, "[TOCTW] updateFavoritesData - failed: ${t.message}", t)
                        })
                )
            }
        }

    }

    override fun updateBoothInfo(booth: Booth) {
        disposable.add(
            boothRepository.updateBoothInfo(booth)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe{view.showWaitingView(true)}
                .doOnError { view.showWaitingView(false) }
                .doOnComplete { view.showWaitingView(false) }
                .subscribe({
                    view.onUpdateBoothInfoSuccess()
                }, { t->
                    Log.e(TAG, "[TOCTW] loadCollection - failed: ${t.message}", t)
                })
        )
    }




}