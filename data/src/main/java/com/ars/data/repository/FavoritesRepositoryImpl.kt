package com.ars.data.repository

import com.ars.data.remote.api.FavoriteProductApi
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.repository.IFavoritesRepository
import com.ars.domain.utils.Resource
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteProductApi: FavoriteProductApi
) : IFavoritesRepository {
    override suspend fun retrieveCustomerFavoriteProducts(id: String): Resource<List<FavoriteProduct>> {
        return try {
            val response = favoriteProductApi.getCustomerFavoriteProducts(id)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun saveCustomerFavoriteProduct(
        favoriteProduct: FavoriteProduct,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        try {
            favoriteProductApi.addProductToCustomerFavorites(favoriteProduct)
            onSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure(e)
        }
    }

    override suspend fun deleteProductFromFavorites(
        id: Int,
        onSuccessDelete: () -> Unit,
        onDeleteFailed: (e: Exception) -> Unit
    ) {
        try {
            favoriteProductApi.deleteCustomerFavoriteProduct(id)
            onSuccessDelete()
        } catch (e: Exception) {
            e.printStackTrace()
            onDeleteFailed(e)
        }
    }
}