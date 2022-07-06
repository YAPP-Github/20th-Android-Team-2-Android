package com.yapp.android2.domain.repository.record

import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.entity.base.Response
import com.yapp.android2.domain.repository.Repository

interface RecordRepository : Repository {
    suspend fun fetchRecordsOrThrow(recordMM: String): List<Item>
    suspend fun updateRecords(product: Product, user: User)
}

data class Item(
    val record: Response,
    val totalCount: Int,
    val timesComparedToPrev: Int
)
