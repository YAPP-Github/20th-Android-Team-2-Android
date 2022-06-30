package com.yapp.android2.data.service

import com.yapp.android2.data.remote.request.PostSavingRecordsRequest
import com.yapp.android2.domain.entity.base.Record
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.entity.base.Response
import com.yapp.android2.domain.entity.base.Summary
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RecordService : Service {

    @GET("/api/savingRecords")
    suspend fun fetchRecords(
        @Query("recordMM") date: String
    ): ApiResponse<List<Response>>

    @GET("/api/savingRecords/summary")
    suspend fun fetchSummaryRecords(
        @Query("recordMM") date: String
    ): ApiResponse<List<Summary>>

    @POST("/api/savingRecords")
    suspend fun updateRecords(
        @Body request: PostSavingRecordsRequest
    ): ApiResponse<Unit>
}
