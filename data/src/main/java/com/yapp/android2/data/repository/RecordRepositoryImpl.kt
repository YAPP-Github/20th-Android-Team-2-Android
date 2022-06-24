package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.record.RecordRemoteDataSource
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.domain.repository.record.RecordRepository
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordRemoteDataSource: RecordRemoteDataSource
): RecordRepository {

    override suspend fun fetchRecords(recordMM: String): List<Item> {

        val totalCount = recordRemoteDataSource.fetchRecords(recordMM).groupBy { record -> record.name }

        return recordRemoteDataSource.fetchRecords(recordMM).map {
            Item(it, totalCount = totalCount.values.flatten().size)
        }
    }

}
