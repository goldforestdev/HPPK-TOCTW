package com.hppk.toctw.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class StampBooth(
    val boothId: String = "",
    val boothName: String = ""
)

@Entity
data class Child(
    @PrimaryKey
    val name: String = "",
    var avatar: Int = 0
)
