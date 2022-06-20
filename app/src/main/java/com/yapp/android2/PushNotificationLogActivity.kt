package com.yapp.android2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.best.friends.core.BaseActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import com.yapp.android2.databinding.ActivityPushNotificationLogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PushNotificationLogActivity :
    BaseActivity<ActivityPushNotificationLogBinding>(R.layout.activity_push_notification_log) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.messaging.subscribeToTopic("noti_all").addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "noti_all topic 구독 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "noti_all topic 구독 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val message = intent?.getParcelableExtra<RemoteMessage>("RemoteMessage")

        message?.let {
            binding.tvTitleContent.text =  message.data["title"]
            binding.tvBodyContent.text = message.data["body"]
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Firebase.messaging.unsubscribeFromTopic("noti_all")
    }
}
