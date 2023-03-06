package com.ars.domain.usercase.cart

import com.ars.domain.model.CartItem
import com.ars.domain.repository.ICartRepository
import javax.inject.Inject

class SaveCartItemUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    suspend operator fun invoke(cartItem: CartItem, onSuccess: () -> Unit, onFailure: (e: Exception) -> Unit) =
        cartRepository.saveCustomerCartItem(cartItem, onSuccess, onFailure)
}