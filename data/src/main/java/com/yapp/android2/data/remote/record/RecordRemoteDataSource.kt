package com.yapp.android2.data.remote.record

import com.yapp.android2.domain.Record

interface RecordRemoteDataSource {
    suspend fun fetchRecords(recordMM: String): List<Record>
}
