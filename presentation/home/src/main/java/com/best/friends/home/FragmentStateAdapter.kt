package com.best.friends.home

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yapp.android2.record.RecordFragment

class FragmentStateAdapter(
    private val fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private enum class TabKind {
        HOME, RECORD
    }

    private val fragments = arrayListOf<Fragment>(
        HomeFragment.newInstance(),
        RecordFragment.newInstance()
    )
    private val context: Context
        get() = fragmentActivity

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun getTabTitle(position: Int): String {
        return when (TabKind.values()[position]) {
            TabKind.HOME -> context.getString(R.string.tab_name_home)
            TabKind.RECORD -> context.getString(R.string.tab_name_record)
        }
    }

    fun getTabSelectedResource(position: Int): Drawable? {
        val resourceId = when (TabKind.values()[position]) {
            TabKind.HOME -> com.best.friend.design.R.drawable.icon_home_active
            TabKind.RECORD -> com.best.friend.design.R.drawable.icon_record_active
        }

        return ContextCompat.getDrawable(context, resourceId)
    }

    fun getTabUnSelectedResource(position: Int): Drawable? {
        val resourceId = when (TabKind.values()[position]) {
            TabKind.HOME -> com.best.friend.design.R.drawable.icon_home_inactive
            TabKind.RECORD -> com.best.friend.design.R.drawable.icon_record_inactive
        }

        return ContextCompat.getDrawable(context, resourceId)
    }
}
