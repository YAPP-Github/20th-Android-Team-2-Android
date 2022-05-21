package com.yapp.android2.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.yapp.android2.presentation.R
import com.yapp.android2.presentation.base.BaseActivity
import com.yapp.android2.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
