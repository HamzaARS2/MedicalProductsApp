package com.ars.groceriesapp.data.network.repository.auth

import com.ars.domain.Resource
import com.ars.groceriesapp.data.network.repository.IFetchRepository
import com.ars.groceriesapp.model.Product

interface IProductsProvider: IFetchRepository<Product,Int> {

    suspend fun retrieveExclusive(): Resource<List<Product>?>
    suspend fun retrieveMostRated(): Resource<List<Product>?>
}