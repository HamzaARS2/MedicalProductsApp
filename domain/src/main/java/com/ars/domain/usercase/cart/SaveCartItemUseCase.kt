package com.ars.domain.usercase.cart

import com.ars.domain.model.CartItem
import com.ars.domain.repository.ICartRepository
import javax.inject.Inject

class SaveCartItemUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    operator fun invoke(customerId: String, productId: Int) =
        cartRepository.saveCustomerCartItem(customerId, productId)
}