package com.yapp.android2.domain.entity.base

import com.yapp.android2.domain.Entity

data class Record(
    val name: String,
    val price: Int,
    val productId: Int,
    val recordYmd: String,
    private val resolution: String?,
    val timesComparedToPrev: Int
): Entity {
    val promise: String
        get() = resolution.orEmpty().ifEmpty { "화이팅" }
}

