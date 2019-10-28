package com.hppk.toctw.data.source.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.hppk.toctw.data.UserNotExistException
import com.hppk.toctw.data.model.User
import com.hppk.toctw.data.source.UserDao
import io.reactivex.Completable
import io.reactivex.Single


private const val USERS = "users"

class FirestoreUserDao(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : UserDao {

    override fun save(user: User) = Completable.create { emitter ->
        db.collection(USERS)
            .document(user.id)
            .set(user)
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener {
                emitter.onComplete()
            }
    }

    override fun get(id: String) = Single.create<User> { emitter ->
        db.collection(USERS)
            .document(id)
            .get()
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener {
                val user: User? = it.toObject(User::class.java)
                if (user == null) {
                    emitter.onError(UserNotExistException(id))
                } else {
                    emitter.onSuccess(user)
                }
            }
    }

}