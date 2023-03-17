package com.ars.domain.usercase.cart

import com.ars.domain.repository.ICartRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    operator fun invoke(id: Int) =
        cartRepository.deleteItemFromCart(id)
}