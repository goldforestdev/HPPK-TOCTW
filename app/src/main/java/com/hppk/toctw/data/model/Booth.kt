package com.hppk.toctw.data.model

data class Booth (
    val id: String = "",
    val booth: String = "",
    val location: String = "",
    val detail: String = "",
    val busy : Busy = Busy.NORMAL
)

enum class Busy {
    VERY_BUSY, BUSY, NORMAL
}