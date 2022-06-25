package com.best.friends.notification

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.best.friends.core.BaseActivity
import com.best.friends.notification.databinding.ActivityNotificationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.yapp.android2.domain.entity.NotificationRequest
import com.yapp.android2.domain.entity.NotificationResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {

    private lateinit var adapter: NotificationAdapter
    private val viewModel by viewModels<NotificationViewModel>()

    @Inject
    lateinit var firebaseMessaging: FirebaseMessaging

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirebase()
        initAdapter()
    }

    private fun initFirebase(){
        firebaseMessaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e(task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            viewModel.addFCMToken(NotificationRequest(token))
            Timber.i("FCM Token: $token")
        })
    }

    private fun initAdapter() {
        adapter = NotificationAdapter()
        binding.rvNotification.adapter = adapter
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val message = intent?.getParcelableExtra<RemoteMessage>("RemoteMessage")
        val data = NotificationResponse.Data(
            message?.data?.get("title").orEmpty(),
            message?.data?.get("body").orEmpty()
        )
        Timber.i("onMessageReceived-onNewIntent: $data")
        adapter.notificationList.add(data)
        adapter.notifyItemInserted(adapter.notificationList.size - 1)
    }
}
