package com.hppk.toctw.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


data class StampBooth(
    val boothId: String = "",
    val boothName: String = ""
) {
    constructor(booth: Booth): this(booth.id, booth.title)
}

@Entity
@Parcelize
data class Child(
    @PrimaryKey
    val name: String = "",
    var avatar: Int = 0
) : Parcelable
