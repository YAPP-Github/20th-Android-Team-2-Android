package com.best.friends.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.best.friends.notification.databinding.ItemNotificationBinding
import com.yapp.android2.domain.entity.NotificationResponse

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    val notificationList = mutableListOf<NotificationResponse.Data>()

    class NotificationViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NotificationResponse.Data){
            binding.data = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.onBind(notificationList[position])
    }

    override fun getItemCount(): Int = notificationList.size
}
