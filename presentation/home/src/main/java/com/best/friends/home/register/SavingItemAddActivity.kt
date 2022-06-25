package com.best.friends.home.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.best.friends.core.BaseActivity
import com.best.friends.home.R
import com.best.friends.home.databinding.ActivitySavingItemAddBinding
import dagger.hilt.android.AndroidEntryPoint

/**
* 절약 추가 화면 Activity
*/
@AndroidEntryPoint
class SavingItemAddActivity :
    BaseActivity<ActivitySavingItemAddBinding>(R.layout.activity_saving_item_add) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(com.best.friend.design.R.drawable.icon_chevron_left)
        supportActionBar?.title = getString(R.string.saving_item_activity_toolbar_title)
    }

    companion object {

        fun intent(context: Context): Intent {
            return Intent(context, SavingItemAddActivity::class.java)
        }
    }
}
