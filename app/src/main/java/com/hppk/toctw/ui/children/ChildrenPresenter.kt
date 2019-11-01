package com.hppk.toctw.ui.children

import android.util.Log
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.repository.ChildrenRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChildrenPresenter (
    private val view: ChildrenContract.View,
    private val childrenRepository: ChildrenRepository,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): ChildrenContract.Presenter {

    private val TAG = ChildrenPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun getChildren() {
        disposable.add(
            childrenRepository.getAll()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onChildrenLoaded(it)
                }, { t ->
                    Log.e(TAG, "[TOCTW] getChildren - failed: ${t.message}", t)
                })
        )
    }

    override fun saveChild(name: String, avatarResId: Int) {
        val child = Child(name, avatarResId)
        disposable.add(
            childrenRepository.save(child)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onChildSaved(child)
                }, { t ->
                    Log.e(TAG, "[TOCTW] saveChild - failed: ${t.message}", t)
                })
        )
    }

}