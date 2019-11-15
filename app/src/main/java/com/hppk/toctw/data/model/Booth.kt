package com.hppk.toctw.data.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Booth (
    val id: String = "",
    val title: String = "",
    val location: String = "",
    val description: String = "",
    val time : String = "11월 16일, 10:00 ~ 15:00",
    val members : List<Staff> = mutableListOf(),
    val category : List<Category> = mutableListOf(),
    var busy : Busy = Busy.NORMAL,
    val locationColorRes : String = "",
    val locationRes : String = "",
    val photoRes: String = "",
    @field:JvmField
    var isStamp: Boolean = false

): Parcelable

@Parcelize
data class Staff (
    val photoUrl : String = "",
    val name : String = ""
): Parcelable

enum class Busy {
    CLOSE, VERY_BUSY, NORMAL, GOOD,
}

enum class Category {
    INFO, EVENT, EXPERIENCE
}

data class Review (
    val userId: String = "",
    val userName: String = "",
    val userProfile: String = "",
    val rating: Float = 5F,
    val review: String = "",
    val createdAt: Long = System.currentTimeMillis()
)