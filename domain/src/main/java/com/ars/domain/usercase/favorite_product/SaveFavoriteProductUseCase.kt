package com.ars.domain.usercase.favorite_product

import com.ars.domain.model.FavoriteProduct
import com.ars.domain.repository.IFavoritesRepository
import javax.inject.Inject

class SaveFavoriteProductUseCase @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) {

    suspend operator fun invoke(
        favoriteProduct: FavoriteProduct,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) =
        favoritesRepository.saveCustomerFavoriteProduct(favoriteProduct, onSuccess, onFailure)
}