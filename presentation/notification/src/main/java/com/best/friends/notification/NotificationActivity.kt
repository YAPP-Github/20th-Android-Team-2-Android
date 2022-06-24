package com.best.friends.notification

import android.content.Intent
import android.os.Bundle
import com.best.friends.core.BaseActivity
import com.best.friends.notification.databinding.ActivityNotificationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.yapp.android2.domain.entity.NotificationResponse
import timber.log.Timber

class NotificationActivity : BaseActivity<ActivityNotificationBinding>(R.layout.activity_notification) {

    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirebase()
        initAdapter()
    }

    private fun initFirebase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e(task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Timber.i("FCM Token: $token")
        })
    }

    private fun initAdapter() {
        adapter = NotificationAdapter()
        binding.rvNotification.adapter = adapter
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Timber.d("----------------onNewIntent----------------")
        val message = intent?.getParcelableExtra<RemoteMessage>("RemoteMessage")
        val data = NotificationResponse.Data(
            message?.notification?.title.orEmpty(),
            message?.notification?.body.orEmpty()
        )
        Timber.tag("onNewIntent").i("$data")
        adapter.notificationList.add(data)
        adapter.notifyItemInserted(adapter.notificationList.size - 1)
    }
}
