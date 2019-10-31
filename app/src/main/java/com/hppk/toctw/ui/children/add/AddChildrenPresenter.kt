package com.hppk.toctw.ui.children.add

import android.util.Log
import com.hppk.toctw.data.model.Child
import com.hppk.toctw.data.model.Gender
import com.hppk.toctw.data.repository.ChildrenRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AddChildrenPresenter (
    private val view: AddChildrenContract.View,
    private val childrenRepository: ChildrenRepository,
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): AddChildrenContract.Presenter {

    private val TAG = AddChildrenPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun saveChild(name: String, gender: Gender) {
        val child = Child(name, gender)

        disposable.add(
            childrenRepository.save(child)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onChildAdded(child)
                    view.clearEditText()
                }, { t ->
                    Log.e(TAG, "[TOCTW] saveChild - failed: ${t.message}", t)
                    view.clearEditText()
                })
        )
    }

}