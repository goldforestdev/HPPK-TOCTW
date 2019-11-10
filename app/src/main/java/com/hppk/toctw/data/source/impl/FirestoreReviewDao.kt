package com.hppk.toctw.data.source.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hppk.toctw.data.BoothNotExistException
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review
import com.hppk.toctw.data.source.BoothDao
import com.hppk.toctw.data.source.ReviewDao
import io.reactivex.Completable
import io.reactivex.Single

private const val BOOTH = "booth"
private const val REVIEWS = "reviews"

class FirestoreReviewDao(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ReviewDao {

    private val TAG = FirestoreReviewDao::class.java.simpleName

    override fun save(booth: Booth, review: Review) = Completable.create { emitter ->
        db.collection(BOOTH)
            .document(booth.id)
            .collection(REVIEWS)
            .document(review.userId)
            .set(review)
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener {
                emitter.onComplete()
            }
    }

    override fun getReviews(booth: Booth) = Single.create<List<Review>> { emitter ->
        db.collection(BOOTH)
            .document(booth.id)
            .collection(REVIEWS)
            .get()
            .addOnSuccessListener { result ->
                emitter.onSuccess(result.toObjects(Review::class.java))
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting Collection: ", exception)
            }
    }

}