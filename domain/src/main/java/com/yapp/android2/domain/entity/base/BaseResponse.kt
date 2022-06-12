package com.yapp.android2.domain.entity.base

abstract class BaseResponse<T> {
    abstract val data: T
    val statusCode: Int = 200
    val message: String = ""
}
