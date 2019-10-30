package com.hppk.toctw.ui.splash

import android.util.Log
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.repository.BoothRepository
import com.hppk.toctw.data.source.impl.FirestoreBoothDao
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SplashPresenter (
    private val view: SplashContract.View,
    private val boothRepository: BoothRepository = BoothRepository(remoteBoothDao =  FirestoreBoothDao()),
    private val ioScheduler: Scheduler = Schedulers.io(),
    private val uiScheduler: Scheduler = AndroidSchedulers.mainThread(),
    private val disposable: CompositeDisposable = CompositeDisposable()
): SplashContract.Presenter {

    private val TAG = SplashPresenter::class.java.simpleName

    override fun unsubscribe() {
        disposable.clear()
    }

    override fun loadData() {
        disposable.add(
            boothRepository.save(getBooth())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({

                }, { t->
                    Log.e(TAG, "[TOCTW] SaveData - failed: ${t.message}", t)
                })

           /* boothRepository.get("openning")
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe({
                    Log.e(TAG, "[TOCTW] loadData : " + it.location)
                }, { t->
                    Log.e(TAG, "[TOCTW] loadData - failed: ${t.message}", t)
                })*/
        )
    }

    private fun getBooth() : Booth {
        return Booth("4", "매직쇼", "4층 휴게실","매직 쇼의 세계에 함께 하세요.")
    }


}