package com.yapp.android2.data.remote.record

import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.entity.base.Record

interface RecordRemoteDataSource {
    suspend fun fetchRecords(recordMM: String): List<Record>
    suspend fun updateRecords(product: Product, user: User)
}
