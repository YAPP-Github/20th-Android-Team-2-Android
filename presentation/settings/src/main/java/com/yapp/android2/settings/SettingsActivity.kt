package com.yapp.android2.settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.ui.showToast
import com.yapp.android2.domain.repository.setting.SettingRepository
import com.yapp.android2.settings.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel by viewModels<SettingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.activity_in_transition, R.anim.activity_stay_transition)

        viewModel.user.flowWithLifecycle(lifecycle = this.lifecycle)
            .filter{ it != SettingRepository.Settings.Init }
            .onEach {
                when(it) {
                    is SettingRepository.Settings.Error -> { showToast("유저 정보가 없습니다.") }
                    is SettingRepository.Settings.Success -> { binding.viewInit(it) }
                    else -> Unit
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun ActivitySettingsBinding.viewInit(value: SettingRepository.Settings.Success) {
        tvUserId.text = value.email
        tvCreatedAt.text = getString(R.string.created_at, value.createAt.format(DateTimeFormatter.ofPattern("yy.MM.dd")))
    }



    override fun onDestroy() {
        overridePendingTransition(R.anim.activity_out_transition, R.anim.activity_stay_transition)
        super.onDestroy()
    }
}
