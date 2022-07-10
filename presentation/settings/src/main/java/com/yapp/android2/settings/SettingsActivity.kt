package com.yapp.android2.settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.ui.showToast
import com.best.friends.navigator.LogoutNavigator
import com.best.friends.navigator.WebViewNavigator
import com.best.friends.navigator.WithDrawNavigator
import com.yapp.android2.domain.key.EMAIL
import com.yapp.android2.domain.repository.setting.SettingRepository
import com.yapp.android2.settings.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel by viewModels<SettingViewModel>()

    @Inject
    lateinit var navigator: WebViewNavigator

    @Inject
    lateinit var logoutNavigator: LogoutNavigator

    @Inject
    lateinit var withDrawNavigator: WithDrawNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.activity_in_transition, R.anim.activity_stay_transition)

        binding.setOnClickListener()

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
        tvVersion.text = packageManager.getPackageInfo(this@SettingsActivity.packageName, 0).versionName
        tvCreatedAt.text = getString(R.string.setting_created_at, LocalDateTime.parse(value.createAt)?.format(DateTimeFormatter.ofPattern("yy.MM.dd")))
    }

    private fun ActivitySettingsBinding.setOnClickListener() {
        ivBack.setOnSingleClickListener(500) {
            finish()
        }
        tvPolicy.setOnSingleClickListener(500) {
            this@SettingsActivity.startActivity(navigator.intent(this@SettingsActivity))
        }

        viewLogout.setOnSingleClickListener {
            startActivity(logoutNavigator.intent(this@SettingsActivity))
        }

        viewUser.setOnSingleClickListener {
            startActivity(withDrawNavigator.intent(this@SettingsActivity).putExtra(EMAIL, binding.tvUserId.text))
        }
    }

    override fun onDestroy() {
        overridePendingTransition(R.anim.activity_out_transition, R.anim.activity_stay_transition)
        super.onDestroy()
    }
}
