package com.best.friends.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.best.friends.notification.databinding.ItemNotificationBinding
import com.yapp.android2.domain.entity.Notification

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    private val notifications = mutableListOf<Notification>()

    fun submitList(list: List<Notification>) {
        this.notifications.clear()
        notifications.addAll(list)
        notifyDataSetChanged()
    }

    class NotificationViewHolder(
        private val binding: ItemNotificationBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: Notification){
            binding.data = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.onBind(notifications[position])
    }

    override fun getItemCount(): Int {
        return notifications.size
    }
}
