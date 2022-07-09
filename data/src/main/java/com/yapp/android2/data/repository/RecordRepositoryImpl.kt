package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.record.RecordRemoteDataSource
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.domain.repository.record.RecordRepository
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordRemoteDataSource: RecordRemoteDataSource
) : RecordRepository {

    override suspend fun fetchRecordsOrThrow(recordMM: String): List<Item> {

        val savingList = recordRemoteDataSource.fetchRecords(recordMM)
        val summaryList = recordRemoteDataSource.fetchSummaryRecord(recordMM)


        val checkedItems = savingList.flatMap { savingItem ->
            summaryList.filter { savingItem.name == it.name }
                .map {
                    Item(
                        record = savingItem,
                        totalCount = it.baseTimes,
                        timesComparedToPrev = it.timesComparedToPrev
                    )
                }
        }

        return checkedItems.distinctBy { it.record.name }
    }

    override suspend fun updateRecords(product: Product, user: User) {
        recordRemoteDataSource.updateRecords(product, user)
    }
}
