package com.yapp.android2.domain.entity.base

data class ApiResponse<T>(
    val data: T?,
    val message: String,
    val statusCode: Int
) {
    val isSuccess: Boolean
        get() {
            return statusCode in 200..299
        }
}
