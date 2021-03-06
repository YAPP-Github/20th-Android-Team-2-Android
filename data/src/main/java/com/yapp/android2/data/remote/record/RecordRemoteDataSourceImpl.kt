package com.yapp.android2.data.remote.record

import com.yapp.android2.data.remote.request.PostSavingRecordsRequest
import com.yapp.android2.data.service.RecordService
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.entity.base.Record
import com.yapp.android2.domain.entity.base.Response
import com.yapp.android2.domain.entity.base.Summary
import javax.inject.Inject

class RecordRemoteDataSourceImpl @Inject constructor(
    private val recordService: RecordService
) : RecordRemoteDataSource {

    override suspend fun fetchRecords(recordMM: String): List<Response> {
        return recordService.fetchRecords(recordMM).data ?: emptyList()
    }

    override suspend fun fetchSummaryRecord(recordMM: String): List<Summary> {
        return recordService.fetchSummaryRecords(recordMM).data ?: emptyList()
    }

    override suspend fun updateRecords(product: Product, user: User) {
        val request = PostSavingRecordsRequest(
            productId = product.productId,
            userId = user.userId,
            today = product.today.orEmpty()
        )

        recordService.updateRecords(request)
    }
}
