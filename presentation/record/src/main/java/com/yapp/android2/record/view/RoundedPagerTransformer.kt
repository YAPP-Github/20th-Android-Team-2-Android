package com.yapp.android2.record.view

import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.viewpager2.widget.ViewPager2
import com.best.friend.design.R
import kotlin.math.abs

fun ViewPager2.setOffsetTransformer(rounded: Float = 30f): ViewPager2.PageTransformer {

    return ViewPager2.PageTransformer { page, position ->

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
