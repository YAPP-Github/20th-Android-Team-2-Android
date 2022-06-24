package com.yapp.android2.domain.repository

import com.yapp.android2.domain.entity.Product

interface ProductsRepository {
    suspend fun getProductsByYmd(recordYmd: String): List<Product>
    suspend fun postProducts(
        userId: Long,
        name: String,
        price: String,
        resolution: String
    )
    suspend fun updateProducts(
        productId: Long,
        userId: Long,
        name: String,
        price: String,
        resolution: String
    )
    suspend fun deleteProduct(productId: Long)
}
