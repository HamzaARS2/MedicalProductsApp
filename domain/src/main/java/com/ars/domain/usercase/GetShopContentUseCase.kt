package com.ars.domain.usercase


import com.ars.domain.usercase.product.GetShopProductsUseCase
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetShopContentUseCase @Inject constructor(
    private val getShopProductsUseCase: GetShopProductsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) {
    operator fun invoke() =
        getShopProductsUseCase().combine(getCategoriesUseCase()) { productsResponse, categoriesResponse ->
            Pair(productsResponse, categoriesResponse)
        }
}