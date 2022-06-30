package com.yapp.android2.domain.entity.base

import com.yapp.android2.domain.Entity

sealed interface Record {
    val name: String
    val recordYmd: String
}

data class Response(
    override val name: String,
    val price: Int,
    val productId: Int,
    override val recordYmd: String,
    private val resolution: String?,
    val timesComparedToPrev: Int
): Entity, Record {
    val promise: String
        get() = resolution.orEmpty().ifEmpty { "화이팅" }
}

data class Summary(
    val baseTimes: Int,
    val prevTimes: Int,
    val timesComparedToPrev: Int,
    override val name: String,
    override val recordYmd: String
): Entity, Record

