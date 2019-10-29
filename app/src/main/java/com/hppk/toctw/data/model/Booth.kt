package com.hppk.toctw.data.model

data class Booth (
    val id: String = "",
    val title: String = "",
    val location: String = "",
    val description: String = "",
    val members : List<Staff> = mutableListOf(),
    val category : List<Category> = mutableListOf(),
    val busy : Busy = Busy.NORMAL
)

data class Staff (
    val photoUrl : String = "",
    val name : String = ""
)

enum class Busy {
    VERY_BUSY, BUSY, NORMAL
}

enum class Category {
    INFO, EVENT, EXPERIENCE
}