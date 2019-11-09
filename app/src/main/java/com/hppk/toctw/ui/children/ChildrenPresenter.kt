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
                .subscribe({ children ->
                    if (children.isNullOrEmpty()) {
                        view.onEmptyChildrenLoaded()
                    } else {
                        view.onChildrenLoaded(children)
                    }
                }, { t ->
                    Log.e(TAG, "[TOCTW] getChildren - failed: ${t.message}", t)
                })
        )
    }

    override fun deleteChild(child: Child) {
        disposable.add(
            childrenRepository.delete(child)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onChildDeleted()
                }, { t ->
                    Log.e(TAG, "[TOCTW] deleteChild - failed: ${t.message}", t)
                })
        )
    }

}