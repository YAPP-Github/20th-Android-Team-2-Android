package com.best.friends.core.ui

import android.content.Context
import kotlin.math.round
import kotlin.math.roundToInt

fun Context.dpToPx(value: Int): Int {
    val density = resources.displayMetrics.density
    return round(value.toFloat() * density).roundToInt()
}