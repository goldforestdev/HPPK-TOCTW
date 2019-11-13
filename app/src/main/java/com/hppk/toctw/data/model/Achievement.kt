package com.hppk.toctw.data.model

import androidx.room.Entity

@Entity
data class Achievement(
    val id: Int = 0,
    val title: String = "",
    val achieveTime: Long = System.currentTimeMillis()
)

val ACHIEVE_1_FIRST_STAMP = 1 to "첫 번째 Stamp 획득"
val ACHIEVE_2_ALL_STAMP = 2 to "모든 Stamp 획득"
val ACHIEVE_3_FIRST_REVIEW = 3 to "첫 번째 리뷰 작성"
val ACHIEVE_4_THIRD_REVIEW = 4 to "리뷰 3개 작성"
val ACHIEVE_5_FIFTH_REVIEW = 5 to "리뷰 5개 작성"
val ACHIEVE_6_ALL_REVIEW = 6 to "모든 리뷰 작성"
