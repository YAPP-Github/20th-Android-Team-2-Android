package com.yapp.android2.data.service

import com.yapp.android2.data.remote.request.PostProductsRequest
import com.yapp.android2.data.remote.request.UpdateProductsRequest
import com.yapp.android2.domain.entity.Product
import com.yapp.android2.domain.entity.base.ApiResponse
import retrofit2.http.*

interface ProductsService {

    @GET("/api/products")
    suspend fun getProducts(
        @Query("recordYmd") recordYmd: String, // 기록 일자(YYYYMMDD) ex)20220601
        @Header("X-API-VERSION") version: String = Service.VERSION
    ): ApiResponse<List<Product>>

    @POST("/api/products")
    suspend fun postProduct(
        @Body request: PostProductsRequest
    )

    @PATCH("/api/products")
    suspend fun updateProduct(
        @Body request: UpdateProductsRequest
    )

    @DELETE("/api/products/{productId}")
    suspend fun deleteProduct(
        @Path("productId") productId: String
    )
}
