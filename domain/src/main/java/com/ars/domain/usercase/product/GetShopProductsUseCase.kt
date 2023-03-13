package com.ars.domain.usercase.product

import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class GetShopProductsUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    operator fun invoke() = productRepository.fetchShopProducts()
}