package com.ars.domain.usercase.favorite_product

import com.ars.domain.repository.IFavoritesRepository
import javax.inject.Inject

class DeleteFavoriteProductUseCase @Inject constructor(
    private val favoriteRepository: IFavoritesRepository
) {

    suspend operator fun invoke(
        id: Int,
        onSuccessDelete: () -> Unit,
        onDeleteFailed: (e: Exception) -> Unit
    ) =
        favoriteRepository.deleteProductFromFavorites(id, onSuccessDelete, onDeleteFailed)
}