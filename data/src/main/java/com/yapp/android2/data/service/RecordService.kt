package com.yapp.android2.data.service

import com.yapp.android2.domain.Record
import com.yapp.android2.domain.entity.base.TemporaryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecordService : Service {

    @GET("/api/savingRecords")
    suspend fun fetchRecords(
        @Query("recordMM") date: String
    ): TemporaryResponse<List<Record>>
}
