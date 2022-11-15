package com.yapp.android2.data.remote.request

data class PostProductsRequest(
    val name: String,
    val freqType: String,
    val freqInterval: String,
    val startYmd: String,
    val endYmd: String
)
