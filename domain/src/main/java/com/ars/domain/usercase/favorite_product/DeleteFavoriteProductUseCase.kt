package com.ars.domain.usercase.favorite_product

import com.ars.domain.repository.IFavoritesRepository
import javax.inject.Inject

class DeleteFavoriteProductUseCase @Inject constructor(
    private val favoriteRepository: IFavoritesRepository
) {

    operator fun invoke(
        customerId: String,
        productId: Int
    ) =
        favoriteRepository.deleteProductFromFavorites(customerId, productId)
}