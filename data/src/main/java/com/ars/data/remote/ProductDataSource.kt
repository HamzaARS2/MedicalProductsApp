package com.ars.data.remote

import com.ars.data.extensions.toProduct
import com.ars.data.model.ProductResponse
import com.ars.data.remote.api.ProductApi
import com.ars.domain.Resource
import com.ars.domain.model.Product
import retrofit2.Response
import javax.inject.Inject

class ProductDataSource @Inject constructor(
    private val productApi: ProductApi
) {

    suspend fun fetchAllProducts(): List<Product> =
        productApi.retrieveAllProducts().map { it.toProduct() }

}