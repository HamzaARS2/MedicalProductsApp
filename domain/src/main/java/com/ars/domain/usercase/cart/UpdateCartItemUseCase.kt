package com.ars.domain.usercase.cart

import com.ars.domain.repository.ICartRepository
import javax.inject.Inject

class UpdateCartItemUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    suspend operator fun invoke(id: Int, quantity: Int) =
        cartRepository.updateCartItemQuantity(id, quantity)
}