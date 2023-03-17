package com.ars.domain.usercase.product

import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    operator fun invoke(customerId: String, productId: Int) =
        productRepository.fetchProductDetails(customerId, productId)
}