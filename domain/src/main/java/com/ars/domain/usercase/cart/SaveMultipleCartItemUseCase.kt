package com.ars.domain.usercase.cart

import com.ars.domain.model.FavoriteProduct
import com.ars.domain.repository.ICartRepository
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveMultipleCartItemUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    operator fun invoke(customerId: String, favoriteProduct: List<FavoriteProduct>): Flow<Response<String>> {
        val productIds = favoriteProduct.map { it.product.id }.toIntArray()
        return cartRepository.saveMultipleCartItems(customerId, productIds)
    }
}