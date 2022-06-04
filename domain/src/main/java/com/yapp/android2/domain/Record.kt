package com.yapp.android2.domain

import java.util.Date

data class Record(
    val name: String,
    val price: Int,
    val productId: Int,
    val recordYmd: Date,
    private val resolution: String?
) {
    val promise: String
        get() = resolution.orEmpty().ifEmpty { "화이팅" }
}

