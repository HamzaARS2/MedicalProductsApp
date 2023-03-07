package com.ars.domain.usercase.cart

import com.ars.domain.model.CartItem
import com.ars.domain.repository.ICartRepository
import javax.inject.Inject

class SaveMultipleCartItemsUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    suspend operator fun invoke(
        cartItems: List<CartItem>,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) =
        cartRepository.saveMultipleCustomerCartItems(cartItems, onSuccess, onFailure)
}