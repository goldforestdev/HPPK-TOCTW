package com.hppk.toctw.data.source.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hppk.toctw.data.model.Notice
import com.hppk.toctw.data.source.NoticeDao
import io.reactivex.Completable
import io.reactivex.Single


private const val NOTICE = "notice"

class FirestoreNoticeDao(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : NoticeDao {

    private val TAG = FirestoreNoticeDao::class.java.simpleName

    override fun save(notice: Notice) = Completable.create { emitter ->
        db.collection(NOTICE)
            .add(notice)
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener {
                emitter.onComplete()
                Log.d(TAG, "DocumentSnapshot written with ID: ${it.id}")
            }
    }

    override fun getDataList() = Single.create<List<Notice>> { emitter ->
        db.collection(NOTICE)
            .orderBy("timeStamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                emitter.onSuccess(result.toObjects(Notice::class.java))
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting Collection: ", exception)
            }
    }
}