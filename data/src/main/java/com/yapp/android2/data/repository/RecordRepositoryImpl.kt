package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.record.RecordRemoteDataSource
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.User
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.domain.repository.record.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecordRepositoryImpl @Inject constructor(
    private val recordRemoteDataSource: RecordRemoteDataSource
) : RecordRepository {

    override suspend fun fetchRecordsOrThrow(recordMM: String): List<Item> {
        val cacheDates = mutableMapOf<String, List<String>>()


        val savingList = withContext(Dispatchers.IO) {
            recordRemoteDataSource.fetchRecords(recordMM)
        }

        val summaryList = withContext(Dispatchers.IO) {
            recordRemoteDataSource.fetchSummaryRecord(recordMM)
        }

        val flatSummary = savingList.flatMap { item ->

            cacheDates[item.name] = emptyList()

            summaryList.filter { item.name == it.name }.map {
                it.copy(recordYmd = item.recordYmd) to item
            }
        }

        val groups = flatSummary.groupBy { it.first.name }

        val checkedItems = flatSummary.map { (summary, record) ->
            Item(
                record = record.copy(price = groups.getValue(summary.name)
                    .sumOf { it.second.price }),
                totalCount = summary.baseTimes,
                timesComparedToPrev = summary.timesComparedToPrev,
                recordDates = groups.getValue(summary.name).map { it.first.recordYmd }
            )
        }

        return checkedItems
    }

    override suspend fun updateRecords(product: Product, user: User) {
        recordRemoteDataSource.updateRecords(product, user)
    }
}
