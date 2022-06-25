package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.products.ProductsRemoteDataSource
import com.yapp.android2.data.remote.request.PostProductsRequest
import com.yapp.android2.data.remote.request.UpdateProductsRequest
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.repository.ProductsRepository
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource
) : ProductsRepository {

    override suspend fun getProductsToday(): List<Product> {
        val now = LocalDateTime.now()
        val recordYmd = DateTimeFormatter.ofPattern("yyyyMMdd").format(now).also {
            Timber.i("--- ProductsRepository recordYmd: $it")
        }

        return getProductsByYmd(recordYmd)
    }

    override suspend fun getProductsByYmd(recordYmd: String): List<Product> {
        return remoteDataSource.getProductsByYmd(recordYmd)
    }

    override suspend fun postProducts(
        userId: Long,
        name: String,
        price: String,
        resolution: String
    ) {
        val request = PostProductsRequest(
            userId = userId,
            name = name,
            price = price,
            resolution = resolution
        )
        return remoteDataSource.postProducts(request)
    }

    override suspend fun updateProducts(
        productId: Long,
        userId: Long,
        name: String,
        price: String,
        resolution: String
    ) {
        val request = UpdateProductsRequest(
            productId = productId,
            userId = userId,
            name = name,
            price = price,
            resolution = resolution
        )
        return remoteDataSource.updateProducts(request)
    }

    override suspend fun deleteProduct(productId: Long) {
        return remoteDataSource.deleteProduct(productId)
    }
}
