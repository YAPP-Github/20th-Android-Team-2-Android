package com.yapp.android2.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.android2.domain.Record
import com.yapp.android2.record.databinding.ItemRecordBinding

class RecordViewHolder(private val view: ItemRecordBinding) : RecyclerView.ViewHolder(view.root) {
    fun onBind(item: Record) {
        view.item = item
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): RecordViewHolder {
            return RecordViewHolder(
                ItemRecordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
