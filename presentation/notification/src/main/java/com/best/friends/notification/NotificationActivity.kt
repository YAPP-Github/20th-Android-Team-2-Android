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
    }

    private fun initAdapter() {
        adapter = NotificationAdapter()
        binding.rvNotification.adapter = adapter
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val message = intent?.getParcelableExtra<RemoteMessage>("RemoteMessage")
        val data = Notification.Data(
            message?.data?.get("title").orEmpty(),
            message?.data?.get("body").orEmpty()
        )
        Timber.i("onMessageReceived-onNewIntent: $data")
        adapter.notificationList.add(data)
        adapter.notifyItemInserted(adapter.notificationList.size - 1)
    }
}
