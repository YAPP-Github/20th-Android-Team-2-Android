package com.yapp.android2.record

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.best.friends.core.ui.dpToPx
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.utils.Size
import com.kizitonwose.calendarview.utils.yearMonth
import com.yapp.android2.domain.entity.base.Record
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.record.view.DayBind
import com.yapp.android2.record.view.currentMonth
import com.yapp.android2.record.view.firstDayOfWeek
import com.yapp.android2.record.view.firstMonth
import com.yapp.android2.record.view.lastMonth
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import org.joda.time.DateTimeField
import org.joda.time.DateTimeUtils
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.Locale

@BindingAdapter("app:totalSaving")
fun TextView.bindTotalSaving(items: List<Item>?) {
    if(items != null) {
        val totalSaving = items.sumOf { it.record.price }

        text = context.getString(R.string.record_total_price, NumberFormat.getInstance(Locale.KOREAN).format(totalSaving))
    }
}

@BindingAdapter("app:savingItems")
fun CalendarView.bindSavingItems(items: List<Item>?) {
    if(items == null) {
        return
    }

    dayBinder = DayBind.newInstance(items)
    setup(firstMonth, lastMonth, firstDayOfWeek)
    scrollToMonth(currentMonth)
}

@BindingAdapter("app:itemSavingMessage")
fun TextView.bindItemSavingMessage(record: Record?) {
    if(record == null) { return }

//    val parse = DateTime.parse(record.recordYmd)
//
//    val prevMonth = if(parse.monthOfYear == DateTimeConstants.JANUARY) { parse.monthOfYear().get() } else { parse.monthOfYear().get() - DateTimeConstants.JANUARY }
//
//    text = context.getString(R.string.record_saving, prevMonth, record.timesComparedToPrev)
}

@BindingAdapter("app:price")
fun TextView.bindingPrice(price: Int?) {
    if(price != null) {
        text = context.getString(R.string.record_price, NumberFormat.getInstance(Locale.KOREAN).format(price))
    }
}
