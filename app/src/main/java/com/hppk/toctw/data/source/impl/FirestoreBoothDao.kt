package com.hppk.toctw.data.source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.hppk.toctw.data.BoothNotExistException
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.source.BoothDao
import io.reactivex.Completable
import io.reactivex.Single


private const val BOOTH = "booth"

class FirestoreBoothDao(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : BoothDao {

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

}