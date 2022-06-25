package com.yapp.android2.domain.repository.record

import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.entity.base.Record
import com.yapp.android2.domain.repository.Repository

interface RecordRepository : Repository {
    suspend fun fetchRecords(recordMM: String): List<Item>
    suspend fun updateRecords(product: Product, user: User)
}

data class Item(
    val record: Record,
    val totalCount: Int
)
