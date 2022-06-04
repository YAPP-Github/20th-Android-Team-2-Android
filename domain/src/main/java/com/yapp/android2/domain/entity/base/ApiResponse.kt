package com.yapp.android2.domain.entity.base

sealed interface ApiResponse<out T> {

    data class Success<out T>(
        val data: T,
        val statusCode: Int,
        val message: String
    ): ApiResponse<T>

    data class Error(val throwable: Throwable): ApiResponse<Nothing>

}

data class TemporaryResponse<T>(
    val data: T,
    val message: String,
    val statusCode: Int
) {
    val isSuccess: Boolean
        get() {
            return statusCode in 200..299
        }
}
