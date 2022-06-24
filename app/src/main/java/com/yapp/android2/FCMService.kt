package com.yapp.android2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import androidx.core.app.NotificationCompat
import com.best.friends.notification.NotificationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FCMService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.i("onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Timber.i("onMessageReceived: ${message.data}")

        val title = message.data["title"].orEmpty()
        val body= message.data["body"].orEmpty()

        val intent = Intent(this, NotificationActivity::class.java)
            .putExtra("RemoteMessage", message)
            .addFlags(FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)

        val channelId = "ChannelID"
        val mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "ChannelName"
            val channelDescription = "ChannelDescription"
            val importance = NotificationManager.IMPORTANCE_HIGH

            // add in API level 26
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
                enableLights(true)
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 100, 200)
                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            }
            mManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_background)
            setAutoCancel(true)
            setDefaults(Notification.DEFAULT_ALL)
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle(title)
            setContentText(body)
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.setContentTitle(title)
            builder.setVibrate(longArrayOf(500, 500))
        }
        mManager.notify(0, builder.build())
    }
}
