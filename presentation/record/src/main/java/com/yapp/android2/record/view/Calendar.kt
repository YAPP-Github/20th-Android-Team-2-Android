package com.yapp.android2.record.view

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.best.friend.design.R as design
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.yearMonth
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.record.databinding.CalendarItemBinding
import org.joda.time.DateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.*

internal class DayBind(private val savingRecords: List<Item>) : DayBinder<DayContainer> {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(container: DayContainer, day: CalendarDay) {
        container.textView.text = day.date.dayOfMonth.toString()
        val savingDays = savingRecords.map { it.record.recordYmd }

        if(day.owner == DayOwner.THIS_MONTH) {
            container.textView.setTextColor(ContextCompat.getColor(container.view.context, design.color.gray4))
        } else {
            container.textView.setTextColor(ContextCompat.getColor(container.view.context, design.color.gray2))
        }

        if(savingDays.any { DateTime.parse(it).dayOfMonth().get() == day.date.dayOfMonth }) {
            container.textView.background = ContextCompat.getDrawable(container.view.context, design.drawable.bg_oval_color_sub)
        }
    }

    override fun create(view: View): DayContainer = DayContainer(CalendarItemBinding.bind(view))


    companion object {
        fun newInstance(savingRecords: List<Item>): DayBind = DayBind(savingRecords)
    }
}

class DayContainer(view: CalendarItemBinding) : ViewContainer(view.root) {
    val textView = view.tvDate
}

internal val currentMonth = YearMonth.now()
internal val firstMonth = currentMonth.minusMonths(10)
internal val lastMonth = currentMonth.plusMonths(10)
internal val firstDayOfWeek = WeekFields.of(Locale.KOREAN).firstDayOfWeek
