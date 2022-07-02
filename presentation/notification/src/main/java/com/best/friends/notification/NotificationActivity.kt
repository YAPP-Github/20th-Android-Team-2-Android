package com.best.friends.notification

import android.os.Bundle
import androidx.activity.viewModels
import com.best.friends.core.BaseActivity
import com.best.friends.notification.databinding.ActivityNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {

    private lateinit var adapter: NotificationAdapter
    private val viewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initAdapter()
        getNotificationList()
        observeNotificationList()
        setBackBtnClickListner()
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

    private fun setBackBtnClickListner() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}
