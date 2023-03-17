package com.ars.data.repository

import com.ars.data.extensions.asFavoriteProduct
import com.ars.data.extensions.asFavoriteProductEntity
import com.ars.data.local.GroceriesDatabase
import com.ars.data.network.api.FavoriteProductApi
import com.ars.data.util.networkBoundResource
import com.ars.domain.model.FavoriteProduct
import com.ars.domain.repository.IFavoritesRepository
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoriteProductApi: FavoriteProductApi,
    private val database: GroceriesDatabase
) : IFavoritesRepository {

    private val favoriteProductDao = database.getFavoriteProductDao()
    override fun fetchCustomerFavoriteProducts(id: String) = networkBoundResource(
        query = {
            favoriteProductDao.retrieveFavoriteProducts(id)
                .map { favorite -> favorite.map { it.asFavoriteProduct() } }
        },

        fetch = {
            favoriteProductApi.fetchCustomerFavoriteProducts(id)
        },

        saveFetchResult = { networkFavoriteProducts ->
            favoriteProductDao.insertFavoriteProduct(networkFavoriteProducts.map { it.asFavoriteProductEntity() })
        }
    )

    override fun saveCustomerFavoriteProduct(
        productId: Int,
        customerId: String
    ) = flow {
        val result = try {
            val count = favoriteProductDao.retrieveFavoriteProductCount(customerId, productId)
            if (count > 0) Response.Error(Throwable("Operation failed!"), "Already in favorites")
            else {
                val networkFavoriteProduct =
                    favoriteProductApi.addProductToCustomerFavorites(productId, customerId)
                favoriteProductDao.insertFavoriteProduct(listOf(networkFavoriteProduct.asFavoriteProductEntity()))
                Response.Success("Added to favorites")
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(
                throwable,
                "Something went wrong!, please check your internet connection"
            )
        }
        emit(result)
    }

    override fun deleteProductFromFavorites(
        customerId: String,
        productId: Int
    ) = flow {
        val result = try {
            favoriteProductApi.deleteCustomerFavoriteProduct(customerId, productId)
            favoriteProductDao.deleteFavoriteProduct(productId, customerId)
            Response.Success("Removed from favorites")
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(
                throwable,
                "Something went wrong!, please check your internet connection"
            )
        }
        emit(result)
    }

}