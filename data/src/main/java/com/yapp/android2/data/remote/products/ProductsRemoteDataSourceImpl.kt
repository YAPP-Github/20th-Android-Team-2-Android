package com.yapp.android2.data.remote.products

import com.yapp.android2.data.remote.request.PostProductsRequest
import com.yapp.android2.data.remote.request.UpdateProductsRequest
import com.yapp.android2.data.service.ProductsService
import com.yapp.android2.domain.entity.Product
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val productsService: ProductsService
) : ProductsRemoteDataSource {

    override suspend fun getProductsByYmd(recordYmd: String): List<Product> {
        return productsService.getProducts(recordYmd)
    }

    override suspend fun postProducts(request: PostProductsRequest) {
        return productsService.postProduct(request)
    }

    override suspend fun updateProducts(request: UpdateProductsRequest) {
        return productsService.updateProduct(request)
    }

    override suspend fun deleteProduct(productId: Long) {
        return productsService.deleteProduct(productId.toString())
    }
}
