package com.ars.domain.repository.product

import com.ars.domain.utils.Resource
import com.ars.domain.model.Product

interface IProductRepository: IFetchRepository<Product, Int> {
    suspend fun retrieveExclusive(): Resource<List<Product>?>
    suspend fun retrieveMostRated(): Resource<List<Product>?>
}