package com.best.friends.core

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter

class OnSingleClickListener(
    private val interval: Int,
    private val onSingleClick: (View) -> Unit
) : View.OnClickListener {

    private var lastClickedTime: Long = 0L

    override fun onClick(v: View) {
        val elapsedRealTime = SystemClock.elapsedRealtime()
        if ((elapsedRealTime - lastClickedTime) < interval) {
            return
        }
        lastClickedTime = elapsedRealTime
        onSingleClick(v)
    }
}

fun View.setOnSingleClickListener(
    interval: Int = 1000,
    onClick: (View) -> Unit = { }
) {
    setOnClickListener(OnSingleClickListener(interval, onClick))
}

@BindingAdapter("android:onClick", "throttleMillis", requireAll = false)
fun setOnSingleClickListener(
    view: View,
    listener: View.OnClickListener?,
    throttleMillis: Long? = 1000
) {
    view.setOnClickListener(listener?.let {
        View.OnClickListener {
            val lastClickedAt = (view.getTag(R.id.last_click_at) as Long?) ?: 0L
            if (System.currentTimeMillis() > lastClickedAt + (throttleMillis ?: 1000L)) {
                listener.onClick(view)
                view.setTag(R.id.last_click_at, System.currentTimeMillis())
            }
        }
    })
}
