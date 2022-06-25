package com.yapp.android2.data.remote.request

data class PostSavingRecordsRequest(
    val productId: Long,
    val userId: Long,
    val today: String
)
