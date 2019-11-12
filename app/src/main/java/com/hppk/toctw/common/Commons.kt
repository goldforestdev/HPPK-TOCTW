package com.hppk.toctw.common

import android.content.Context
import com.hppk.toctw.R
import java.text.SimpleDateFormat
import java.util.*

val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)

fun Context.getAvatarResId(resName: String): Int {
    var imgResId = resources.getIdentifier(resName, "drawable", packageName)
    if (imgResId == 0) {
        imgResId = R.drawable.ic_unknown_kid
    }

    return imgResId
}