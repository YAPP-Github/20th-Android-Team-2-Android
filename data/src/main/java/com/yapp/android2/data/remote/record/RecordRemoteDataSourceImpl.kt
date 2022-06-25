package com.yapp.android2.data.remote.record

import com.yapp.android2.data.service.RecordService
import com.yapp.android2.domain.entity.base.Record
import javax.inject.Inject

class RecordRemoteDataSourceImpl @Inject constructor(
    private val recordService: RecordService
) : RecordRemoteDataSource {

    override suspend fun fetchRecords(recordMM: String): List<Record> {
        return recordService.fetchRecords(recordMM).data
    }

}
