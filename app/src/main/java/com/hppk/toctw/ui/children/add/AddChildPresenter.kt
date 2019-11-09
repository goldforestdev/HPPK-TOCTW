package com.hppk.toctw.ui.children.add

import android.util.Log
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.repository.ChildrenRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddChildPresenter (
    private val view: AddChildContract.View,
    private val childrenRepository: ChildrenRepository,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): AddChildContract.Presenter {

    private val TAG = AddChildPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun saveChild(childName: String, avatarResId: Int) {
        val child = Child(childName, avatarResId)
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