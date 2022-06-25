package com.yapp.android2.domain.repository

import com.yapp.android2.domain.entity.Product

interface ProductsRepository {
    suspend fun getProductsToday(): List<Product>
    suspend fun getProductsByYmd(recordYmd: String): List<Product> // 기록 일자(YYYYMMDD) ex)20220601
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
