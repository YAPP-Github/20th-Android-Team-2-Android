package com.yapp.android2.domain.entity.base

sealed interface ApiResponse<T> {

    data class Success<T>(
        val data: T,
        val statusCode: Int,
        val message: String
    ): ApiResponse<T>

    data class Error(val throwable: Throwable): ApiResponse<Nothing>

}

