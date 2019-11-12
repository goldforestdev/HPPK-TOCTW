package com.hppk.toctw.data.source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hppk.toctw.data.model.Booth
import com.hppk.toctw.data.model.Review
import com.hppk.toctw.data.source.ReviewDao
import io.reactivex.Completable
import io.reactivex.Single

private const val BOOTH = "booth"
private const val REVIEWS = "reviews"

class FirestoreReviewDao(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ReviewDao {

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

    override fun getLittleReviews(booth: Booth) = Single.create<List<Review>> { emitter ->
        db.collection(BOOTH)
            .document(booth.id)
            .collection(REVIEWS)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .limit(4)
            .get()
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener { result ->
                emitter.onSuccess(result.toObjects(Review::class.java))
            }
    }

    override fun getReviews(booth: Booth) = Single.create<List<Review>> { emitter ->
        db.collection(BOOTH)
            .document(booth.id)
            .collection(REVIEWS)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener { result ->
                emitter.onSuccess(result.toObjects(Review::class.java))
            }
    }

}