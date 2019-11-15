package com.hppk.toctw.ui.booth

import android.util.Log
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Favorites
import com.hppk.toctw.data.repository.BoothRepository
import com.hppk.toctw.data.repository.FavoritesRepository
import com.hppk.toctw.data.source.impl.FirestoreBoothDao
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
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
    private var favorites = Favorites()

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
        val boothList = boothRepository.getBootList()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)


        val favoritesList = favoritesRepository.getAll()
            .subscribeOn(ioScheduler)

        disposable.add(
            Single.zip(boothList, favoritesList
                , BiFunction<List<Booth>,List<Favorites>,Pair<List<Booth>,List<Favorites>>>{t1, t2 -> Pair(t1,t2) })
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .doOnSubscribe {
                    view.showWaitingView(true)
                }
                .doOnError { view.showWaitingView(false) }
                .doOnSuccess { view.showWaitingView(false) }
                .subscribe({
                    view.onBoothListLoaded(it.first, it.second)
                }, { e ->
                    view.showWaitingView(false)
                    Log.e(TAG, "[TOCTW] boothInfo - failed: ${e.message}", e)
                })
        )
    }


    override fun saveFavoritesData(booth: Booth) {
        favorites.id = booth.id
        disposable.add(
            favoritesRepository.save(favorites)
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

    override fun deleteFavoritesData(booth: Booth) {
        favorites.id = booth.id
        disposable.add(
            favoritesRepository.delete(favorites)
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