package com.best.friends.core.extensions

import java.time.ZonedDateTime

val ZonedDateTime.isToday: Boolean
    get() {
        val now = ZonedDateTime.now()
        return year == now.year &&
            monthValue == now.monthValue &&
            dayOfMonth == now.dayOfMonth
    }

val ZonedDateTime.isNotToday: Boolean
    get() = !isToday
