package com.yapp.android2.domain.entity.base

data class ApiResponse<T>(
    val data: T,
    val statusCode: Int,
    val message: String
)
