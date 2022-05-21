package com.best.friends.seconds

import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.navigator.HomeNavigator
import com.best.friends.seconds.databinding.ActivitySecondsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondsActivity : BaseActivity<ActivitySecondsBinding>(R.layout.activity_seconds) {

    private val viewModel by viewModels<SecondsViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvNavigation.setOnClickListener {
            val intent = homeNavigator.intent(this)
            startActivity(intent)
        }
    }
}
