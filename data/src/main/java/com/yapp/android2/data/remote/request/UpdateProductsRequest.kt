package com.yapp.android2.data.remote.request

data class UpdateProductsRequest(
    val productId: Long,
    val userId: Long,
    val name: String,
    val price: String
)
