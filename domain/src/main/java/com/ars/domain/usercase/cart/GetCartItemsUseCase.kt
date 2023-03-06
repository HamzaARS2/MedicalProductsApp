package com.ars.domain.usercase.cart

import com.ars.domain.repository.ICartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    suspend operator fun invoke(id: String) =
        cartRepository.retrieveCustomerCartItems(id)
}