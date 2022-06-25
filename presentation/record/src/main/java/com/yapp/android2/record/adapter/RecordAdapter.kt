package com.yapp.android2.record.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.record.RecordViewHolder

class RecordAdapter : ListAdapter<Item, RecordViewHolder>(DIFF_UTIL) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent, false)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return false
            }
        }
    }
}
