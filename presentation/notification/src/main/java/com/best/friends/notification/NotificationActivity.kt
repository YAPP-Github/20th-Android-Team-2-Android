package com.best.friends.notification

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.notification.databinding.ActivityNotificationBinding
import com.google.firebase.messaging.RemoteMessage
import com.yapp.android2.domain.entity.Notification
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {

    private lateinit var adapter: NotificationAdapter
    private val viewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        getNotificationList()
        observeNotificationList()
    }

    private fun initAdapter() {
        adapter = NotificationAdapter()
        binding.rvNotification.adapter = adapter
    }

    private fun getNotificationList() = viewModel.getNotificationList()

    private fun observeNotificationList() {
        viewModel.notificationList.observe(this) {
            adapter.notificationList.clear()
            adapter.notificationList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }
}
