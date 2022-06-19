package com.best.friends.core.ui

import android.content.Context
import android.util.TypedValue
import kotlin.math.round
import kotlin.math.roundToInt

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()