package com.reddevx.groceriesapp.data.network.repository.auth

import com.reddevx.groceriesapp.data.network.Resource
import com.reddevx.groceriesapp.data.network.repository.IFetchRepository
import com.reddevx.groceriesapp.model.Product

interface IProductsProvider: IFetchRepository<Product,Int> {

    suspend fun retrieveExclusive(): Resource<List<Product>?>
    suspend fun retrieveMostRated(): Resource<List<Product>?>
}