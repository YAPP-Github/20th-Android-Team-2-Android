package com.yapp.android2.record

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.best.friends.core.ui.dpToPx
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.InDateStyle
import com.kizitonwose.calendarview.model.OutDateStyle
import com.kizitonwose.calendarview.utils.Size
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.record.view.DayBind
import com.yapp.android2.record.view.currentMonth
import com.yapp.android2.record.view.firstDayOfWeek
import com.yapp.android2.record.view.firstMonth
import com.yapp.android2.record.view.lastMonth
import java.text.NumberFormat
import java.util.Locale

@BindingAdapter("app:totalSaving")
fun TextView.bindTotalSaving(items: List<Item>?) {
    if(items != null) {
        val totalSaving = items.sumOf { it.record.price }

        text = context.getString(R.string.record_title, NumberFormat.getInstance(Locale.KOREAN).format(totalSaving))
    }
}

@BindingAdapter("app:savingItems")
fun CalendarView.bindSavingItems(items: List<Item>?) {
    if(items.isNullOrEmpty()) {
        return
    }

    dayBinder = DayBind.newInstance(items)
    daySize = Size(context.dpToPx(50), context.dpToPx(50))
    inDateStyle = InDateStyle.ALL_MONTHS
    outDateStyle = OutDateStyle.END_OF_ROW
    setup(firstMonth, lastMonth, firstDayOfWeek)
    scrollToMonth(currentMonth)
}
