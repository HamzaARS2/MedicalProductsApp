package com.ars.domain.repository.product

import com.ars.domain.model.OnSaleProduct
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.model.ProductDetails

interface IProductRepository: IFetchRepository<ProductDetails, Int> {
    suspend fun retrieveExclusive(): Resource<List<Product>?>
    suspend fun retrieveOnSaleProducts(): Resource<List<OnSaleProduct>?>
    suspend fun retrieveMostRated(): Resource<List<Product>?>
}