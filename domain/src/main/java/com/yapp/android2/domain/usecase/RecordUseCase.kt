package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.UseCase
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.domain.repository.record.RecordRepository
import javax.inject.Inject


class GetRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
): RecordUseCase {

    override suspend fun execute(params: RecordUseCase.Params): List<Item> {
        return recordRepository.fetchRecords(params.date)
    }
}

interface RecordUseCase : UseCase<RecordUseCase.Params, List<Item>> {
    @JvmInline
    value class Params(val date: String)
}
