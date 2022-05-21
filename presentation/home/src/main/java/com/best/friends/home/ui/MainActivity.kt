package com.best.friends.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.home.R
import com.best.friends.home.base.BaseActivity
import com.best.friends.home.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
