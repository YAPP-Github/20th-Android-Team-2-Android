package com.yapp.android2.data.remote.request

data class PostProductsRequest(
    val userId: Long,
    val name: String,
    val price: String,
    val resolution: String
)
