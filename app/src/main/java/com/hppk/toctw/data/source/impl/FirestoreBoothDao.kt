package com.hppk.toctw.data.source.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hppk.toctw.data.BoothNotExistException
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Busy
import com.hppk.toctw.data.source.BoothDao
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

    override fun getBoothList() = Single.create<List<Booth>> { emitter ->
        db.collection(BOOTH)
            //.orderBy("id")
            .whereEqualTo("busy","GOOD")
            .whereEqualTo("isStamp",true)
            .orderBy("id")
            .get()
            .addOnSuccessListener { result ->
                emitter.onSuccess(result.toObjects(Booth::class.java))
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting Collection: ", exception)
            }
    }

    override fun getStampBoothList() = Single.create<List<Booth>> { emitter ->
        db.collection(BOOTH)
            .whereEqualTo("isStamp", true)
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "[TOCTW] getStampBoothList - success")
                emitter.onSuccess(result.toObjects(Booth::class.java))
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting Collection: ", exception)
            }
    }

    override fun updateBooth(booth: Booth) = Completable.create { emitter ->
        db.collection(BOOTH)
            .document(booth.id)
            .set(booth)
            .addOnSuccessListener {
                emitter.onComplete()
            }
            .addOnFailureListener { exception ->
                emitter.onError(exception)
                Log.e(TAG, "Error getting Collection: ", exception)
            }
    }

    override fun getBoothBusyStatusList(busyStatus: List<Busy>) =  Single.create<List<Booth>>  { emitter ->
        val queryDataList = mutableListOf<Booth>()
        for (data in busyStatus) {
            db.collection(BOOTH)
                .whereEqualTo("busy", data)
                .get()
                .addOnSuccessListener { result ->
                    Log.d(TAG, "[TOCTW] getStampBoothList - success")
                    queryDataList.addAll( result.toObjects(Booth::class.java))

                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Error getting Collection: ", exception)
                }
        }

        emitter.onSuccess(queryDataList)
    }

}