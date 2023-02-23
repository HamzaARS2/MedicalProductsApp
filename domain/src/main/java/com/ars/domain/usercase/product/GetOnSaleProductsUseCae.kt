package com.ars.domain.usercase.product

import com.ars.domain.model.OnSaleProduct
import com.ars.domain.repository.product.IProductRepository
import com.ars.domain.utils.Resource
import javax.inject.Inject

class GetOnSaleProductsUseCae @Inject constructor(
    private val productRepository: IProductRepository
) {

    suspend operator fun invoke() =
        productRepository.retrieveOnSaleProducts()
}