package com.ars.domain.usercase.favorite_product

import com.ars.domain.repository.IFavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val favoritesRepository: IFavoritesRepository
) {

    operator fun invoke(id: String) =
        favoritesRepository.fetchCustomerFavoriteProducts(id)
}