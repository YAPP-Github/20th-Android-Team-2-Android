package com.yapp.android2.domain.repository

import com.yapp.android2.domain.entity.Product
import java.time.ZonedDateTime

interface ProductsRepository {
    suspend fun getProductsToday(): List<Product>
    suspend fun getProductsByZonedDateTime(zonedDateTime: ZonedDateTime): List<Product>
    suspend fun getProductsByYmd(recordYmd: String): List<Product> // 기록 일자(YYYYMMDD) ex)20220601
    suspend fun postProducts(
        name: String,
        freqType: String,
        freqInterval: String,
        startYmd: String,
        endYmd: String
    )
    suspend fun updateProducts(
        productId: Long,
        userId: Long,
        name: String,
        price: String
    )
    suspend fun deleteProduct(productId: Long)
}
