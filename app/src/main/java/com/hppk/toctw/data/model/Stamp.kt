package com.hppk.toctw.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class StampBooth(
    val boothId: String = "",
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
    primaryKeys = ["name", "boothId"],
    foreignKeys = [ForeignKey(
        entity = Child::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("name")
    ), ForeignKey(
        entity = StampBooth::class,
        parentColumns = arrayOf("boothId"),
        childColumns = arrayOf("boothId")
    )]
)
data class ChildStampJoin(
    val name: String,
    val boothId: String
)