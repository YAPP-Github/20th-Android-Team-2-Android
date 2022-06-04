package com.yapp.android2.domain.repository.record

import com.yapp.android2.domain.Record
import com.yapp.android2.domain.repository.Repository

interface RecordRepository : Repository {
    suspend fun fetchRecords(recordMM: String): List<Item>
}

data class Item(
    val record: Record,
    val totalCount: Int,
)
