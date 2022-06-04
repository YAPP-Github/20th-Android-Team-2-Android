package com.best.friends.home

import android.os.Bundle
import com.best.friends.core.BaseActivity
import com.best.friends.home.databinding.ActivitySavingItemEmptyBinding
import com.best.friends.navigator.HomeNavigator
import javax.inject.Inject

class SavingItemEmptyActivity :
    BaseActivity<ActivitySavingItemEmptyBinding>(R.layout.activity_saving_item_empty)  {

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvSavingItemsAdd.setOnClickListener {
            val intent = homeNavigator.intent(this)
            startActivity(intent)
        }
    }
}
