package com.ars.domain.usercase.product

import com.ars.domain.repository.product.IProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {

    operator fun invoke(query: String, categoryId: Int) =
        productRepository.searchProducts(query, categoryId)
}