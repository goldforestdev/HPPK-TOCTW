package com.hppk.toctw.data.model

import com.google.firebase.Timestamp

data class Notice(
    val title: String = "",
    val body: String = "",
    val timeStamp: Timestamp = Timestamp.now()
)
