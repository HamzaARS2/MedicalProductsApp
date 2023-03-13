package com.ars.domain.repository

import com.ars.domain.model.FavoriteProduct
import com.ars.domain.utils.Resource

interface IFavoritesRepository {

    suspend fun retrieveCustomerFavoriteProducts(id: String): Resource<List<FavoriteProduct>>
    suspend fun saveCustomerFavoriteProduct(favoriteProduct: FavoriteProduct, onSuccess: () -> Unit, onFailure: (e: Exception) -> Unit)
    suspend fun deleteProductFromFavorites(id: Int, onSuccessDelete:() -> Unit, onDeleteFailed: (e: Exception) -> Unit)
    suspend fun deleteProductFromFavorites(customerId: String, productId: Int, onSuccess:() -> Unit, onFailure: (e: Exception) -> Unit)

}