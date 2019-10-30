package com.hppk.toctw.data.source.impl

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.hppk.toctw.data.BoothNotExistException
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.source.BoothDao
import com.hppk.toctw.ui.splash.SplashPresenter
import io.reactivex.Completable
import io.reactivex.Single


private const val BOOTH = "booth"

class FirestoreBoothDao(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : BoothDao {

    private val TAG = FirestoreBoothDao::class.java.simpleName

    override fun save(booth: Booth) = Completable.create { emitter ->
        db.collection(BOOTH)
            .document(booth.id)
            .set(booth)
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener {
                emitter.onComplete()
            }
    }

    override fun get(id: String) = Single.create<Booth> { emitter ->
        db.collection(BOOTH)
            .document(id)
            .get()
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener {
                val booth: Booth? = it.toObject(Booth::class.java)
                if (booth == null) {
                    emitter.onError(BoothNotExistException(id))
                } else {
                    emitter.onSuccess(booth)
                }
            }
    }

    override fun getDataList() = Single.create<List<Booth>> { emitter ->
        db.collection(BOOTH)
            .get()
            .addOnSuccessListener { result ->
                emitter.onSuccess(result.toObjects(Booth::class.java))
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting Collection: ", exception)
            }

    }

}