package com.yapp.android2.record

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yapp.android2.domain.Record

class RecordAdapter : ListAdapter<Record, RecordViewHolder>(DIFF_UTIL) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent, false)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size


    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Record>() {
            override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
                return false
            }
        }
    }
}
