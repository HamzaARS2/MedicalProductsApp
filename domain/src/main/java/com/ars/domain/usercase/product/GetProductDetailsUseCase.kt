package com.ars.domain.usercase.product

import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(id: Int) =
        productRepository.retrieve(id)
}