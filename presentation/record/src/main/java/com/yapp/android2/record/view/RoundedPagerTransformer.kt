package com.yapp.android2.record.view

import android.graphics.drawable.GradientDrawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.best.friend.design.R
import com.best.friends.core.ui.dpToPx
import kotlin.math.abs

fun ViewPager2.setOffsetTransformer(rounded: Float = 30f) {
    val itemMargin = dpToPx(20, context)
    val offset = dpToPx(30, context)
    setPageTransformer { page, position ->
        if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
            page.translationX = position * (offset * 2 + itemMargin)
        } else {
            page.translationX = position * -(offset * 2 + itemMargin)
        }

        val startColor = ContextCompat.getColor(context, R.color.color_sub)
        val endColor = ContextCompat.getColor(context, R.color.color_sub_25)

        val color = ColorUtils.blendARGB(startColor, endColor, abs(position))

        val item = GradientDrawable().apply {
            this.setColor(color)
            this.cornerRadius = rounded
        }

        page.background = item
    }
}
