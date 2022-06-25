package com.yapp.android2.domain.usecase

import com.yapp.android2.domain.UseCase
import com.yapp.android2.domain.repository.record.Item
import com.yapp.android2.domain.repository.record.RecordRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetRecordUseCase @Inject constructor(
    private val recordRepository: RecordRepository
): RecordUseCase {

    override suspend fun execute(params: Unit): List<Item> {
        val now = LocalDate.now()
        val reformatDate = DateTimeFormatter.ofPattern("yyyyMM").format(now)

        return recordRepository.fetchRecords(reformatDate)
    }
}

interface RecordUseCase : UseCase<Unit, List<Item>> {
    @JvmInline
    value class Params(val date: String)
}
