package com.hppk.toctw.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorites(
    @PrimaryKey
    val id: String = "",
    var isFavorite: Int = 0
)
