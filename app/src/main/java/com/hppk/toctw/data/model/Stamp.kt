package com.hppk.toctw.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class Stamp(
    @PrimaryKey
    val id: String = "",
    val boothName: String = "",
    val boothLocation: String = "",
    var isDone: Boolean = false
) : Parcelable {
    constructor(booth: Booth): this(booth.id, booth.title, booth.location)
}

@Entity
@Parcelize
data class Child(
    @PrimaryKey
    val name: String = "",
    var avatar: Int = 0
) : Parcelable

@Entity(
    tableName = "child_stamp_join",
    primaryKeys = ["childName", "stampId"],
    foreignKeys = [ForeignKey(
        entity = Child::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("childName")
    ), ForeignKey(
        entity = Stamp::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("stampId")
    )]
)
data class ChildStampJoin(
    val childName: String,
    val stampId: String
)