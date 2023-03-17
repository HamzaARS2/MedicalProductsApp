package com.ars.domain.usercase.favorite_product

import com.ars.domain.model.FavoriteProduct
import com.ars.domain.repository.IFavoritesRepository
import javax.inject.Inject

class SaveFavoriteProductUseCase @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) {

    operator fun invoke(
        productId: Int,
        customerId: String
    ) =
        favoritesRepository.saveCustomerFavoriteProduct(productId, customerId)
}