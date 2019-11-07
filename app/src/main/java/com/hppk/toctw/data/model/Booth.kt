package com.hppk.toctw.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Booth (
    val id: String = "",
    val title: String = "",
    val location: String = "",
    val description: String = "",
    val members : List<Staff> = mutableListOf(),
    val category : List<Category> = mutableListOf(),
    var busy : Busy = Busy.NORMAL,
    val floor: Floor = Floor.FIVE,
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

enum class Floor {
    FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
}

enum class Category {
    INFO, EVENT, EXPERIENCE
}