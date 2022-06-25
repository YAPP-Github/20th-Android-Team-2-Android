package com.best.friends.home

import com.google.android.material.tabs.TabLayout

abstract class AbstractTabSelectedListener : TabLayout.OnTabSelectedListener {

    override fun onTabSelected(tab: TabLayout.Tab) {
        /* explicitly empty */
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {
        /* explicitly empty */
    }

    override fun onTabReselected(tab: TabLayout.Tab) {
        /* explicitly empty */
    }
}
