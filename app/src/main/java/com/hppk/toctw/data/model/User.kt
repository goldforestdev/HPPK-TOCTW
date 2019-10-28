package com.hppk.toctw.data.model

data class User (
    val id: String,
    val email: String,
    val name: String,
    val role: Role = Role.STAFF
)

enum class Role{
    ADMIN, STAFF
}