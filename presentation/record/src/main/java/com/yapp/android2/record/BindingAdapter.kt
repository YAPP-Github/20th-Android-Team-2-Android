package com.yapp.android2.record

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yapp.android2.domain.repository.record.Item
import java.text.NumberFormat
import java.util.*

@BindingAdapter("app:totalSaving")
fun TextView.bindTotalSaving(items: List<Item>?) {
    if(items != null) {
        val totalSaving = items.sumOf { it.record.price }

        text = context.getString(R.string.record_title, NumberFormat.getInstance(Locale.KOREAN).format(totalSaving))
    }
}
