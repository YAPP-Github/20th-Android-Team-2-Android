package com.best.friends.notification

import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.core.setOnSingleClickListener
import com.best.friends.core.extensions.visibleOrGone
import com.best.friends.notification.databinding.ActivityNotificationBinding
import com.yapp.android2.deeplink.DeepLinkPrefixSpec
import dagger.hilt.android.AndroidEntryPoint

@DeepLinkPrefixSpec(
    value = [
        "notifications"
    ]
)
@AndroidEntryPoint
class NotificationActivity :
    BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {

    private val adapter by lazy { NotificationAdapter() }
    private val viewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        initView()
        observe()
    }

    private fun initAdapter() {
        binding.rvNotification.itemAnimator = null
        binding.rvNotification.adapter = adapter
    }

    private fun initView() {
        binding.ivBack.setOnSingleClickListener {
            finish()
        }

        binding.tvToolbarTitle.text = getString(R.string.toolbar_title_notification)
    }

    private fun observe() {
        viewModel.notificationList.observe(this) { notifications ->
            binding.rvNotification.visibleOrGone(notifications.isNotEmpty())
            binding.tvAlarmEmpty.visibleOrGone(notifications.isEmpty())

            adapter.submitList(notifications)
        }
    }
}
