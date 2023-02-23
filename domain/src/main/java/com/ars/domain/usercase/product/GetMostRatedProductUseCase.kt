package com.ars.domain.usercase.product

import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class GetMostRatedProductUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke() =
        productRepository.retrieveMostRated()
}