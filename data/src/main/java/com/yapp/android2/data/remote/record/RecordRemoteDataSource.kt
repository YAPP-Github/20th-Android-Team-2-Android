package com.yapp.android2.data.remote.record

import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.entity.base.Record
import com.yapp.android2.domain.entity.base.Response
import com.yapp.android2.domain.entity.base.Summary

interface RecordRemoteDataSource {
    suspend fun fetchRecords(recordMM: String): List<Response>
    suspend fun fetchSummaryRecord(recordMM: String): List<Summary>
    suspend fun updateRecords(product: Product, user: User)
}
