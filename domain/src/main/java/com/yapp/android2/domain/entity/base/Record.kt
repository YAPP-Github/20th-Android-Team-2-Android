package com.yapp.android2.domain.entity.base

import com.yapp.android2.domain.Entity
import java.util.Date

data class Record(
    val name: String,
    val price: Int,
    val productId: Int,
    val recordYmd: Date,
    private val resolution: String?
): Entity {
    val promise: String
        get() = resolution.orEmpty().ifEmpty { "화이팅" }
}

