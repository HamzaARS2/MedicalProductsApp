package com.ars.domain.repository

import com.ars.domain.model.FavoriteProduct
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {

    fun fetchCustomerFavoriteProducts(id: String): Flow<Response<List<FavoriteProduct>>>
    fun saveCustomerFavoriteProduct(productId: Int, customerId: String): Flow<Response<String>>
    fun deleteProductFromFavorites(customerId: String, productId: Int): Flow<Response<String>>

}