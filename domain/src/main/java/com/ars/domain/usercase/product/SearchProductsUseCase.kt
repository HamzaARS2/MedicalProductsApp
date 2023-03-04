package com.ars.domain.usercase.product

import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke(query: String) =
        productRepository.searchProducts(query)
}