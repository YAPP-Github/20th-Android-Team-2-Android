package com.yapp.android2.record

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.kizitonwose.calendarview.CalendarView
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.record.view.*
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.util.*

@BindingAdapter("app:totalSaving")
fun TextView.bindTotalSaving(items: List<Item>?) {
    if(items != null) {
        val totalSaving = items.sumOf { it.record.price }

        text = context.getString(R.string.record_total_price, NumberFormat.getInstance(Locale.KOREAN).format(totalSaving))
    }
}

@BindingAdapter("app:itemSavingMessage")
fun TextView.bindItemSavingMessage(timesComparedToPrev: Int?) {
    if(timesComparedToPrev == null) { return }

    val now = LocalDateTime.now()

    val prevMonth = if(now.month == Month.JANUARY) {
        Month.DECEMBER.value
    } else {
        now.monthValue.minus(1)
    }

    text = context.getString(R.string.record_saving, prevMonth, timesComparedToPrev)
}

@BindingAdapter("app:price")
fun TextView.bindingPrice(price: Int?) {
    if(price != null) {
        text = context.getString(R.string.record_price, NumberFormat.getInstance(Locale.KOREAN).format(price))
    }
}
