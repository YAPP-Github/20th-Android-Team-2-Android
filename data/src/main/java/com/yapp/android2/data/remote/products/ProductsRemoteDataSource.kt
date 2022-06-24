package com.yapp.android2.data.remote.products

import com.yapp.android2.data.remote.request.PostProductsRequest
import com.yapp.android2.data.remote.request.UpdateProductsRequest
import com.yapp.android2.domain.entity.Product

interface ProductsRemoteDataSource {
    suspend fun getProductsByYmd(recordYmd: String): List<Product>
    suspend fun postProducts(request: PostProductsRequest)
    suspend fun updateProducts(request: UpdateProductsRequest)
    suspend fun deleteProduct(productId: Long)
}
