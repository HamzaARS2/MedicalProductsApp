package com.ars.domain.usercase.cart

import com.ars.domain.repository.ICartRepository
import javax.inject.Inject

class DeleteCartItemUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {

    suspend operator fun invoke(id: Int, onSuccessDelete:() -> Unit, onDeleteFailed: (e: Exception) -> Unit) =
        cartRepository.deleteItemFromCart(id, onSuccessDelete, onDeleteFailed)
}