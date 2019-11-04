package com.hppk.toctw.ui.home

import android.util.Log
import com.hppk.toctw.data.model.Notice
import com.hppk.toctw.data.repository.NoticeRepository
import com.hppk.toctw.data.source.impl.FirestoreNoticeDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoticePresenter(
    private val view: NoticeContract.View,
    private val noticeRepository: NoticeRepository = NoticeRepository(remoteNoticeDao = FirestoreNoticeDao()),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : NoticeContract.Presenter {

    private val TAG = NoticePresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun addNotice(notice: Notice) {
        disposable.add(
            noticeRepository.save(notice)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.e(TAG, "[TOCTW] addNotice : " + notice.title)
                    view.onAddNoticeSuccess(notice)
                }, { t->
                    Log.e(TAG, "[TOCTW] addNotice - failed: ${t.message}", t)
                    view.onError()
                })
        )
    }


    override fun loadCollection() {
        disposable.add(
            noticeRepository.getDataList()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    view.onNoticeListLoaded(it)
                }, { t ->
                    Log.e(TAG, "[TOCTW] loadCollection - failed: ${t.message}", t)
                    view.onError()
                })
        )
    }
}