package com.ars.data.repository.product


import androidx.room.withTransaction
import com.ars.data.extensions.asDiscountEntity
import com.ars.data.extensions.asProduct
import com.ars.data.extensions.asProductDetails
import com.ars.data.extensions.asProductEntity
import com.ars.data.local.GroceriesDatabase
import com.ars.data.local.entity.DiscountEntity
import com.ars.data.network.ProductDataSource
import com.ars.data.util.networkBoundResource
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.repository.product.IProductRepository
import com.ars.domain.utils.Response

import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val database: GroceriesDatabase
) : IProductRepository {

    private val productDao = database.getProductDao()
    private val discountDao = database.getDiscountDao()
    private val favoriteProductDao = database.getFavoriteProductDao()

    override fun fetchProductDetails(customerId: String?, productId: Int) = flow {
        emit(Response.Loading())
        val response = try {
            var count = 0
            if (customerId != null) {
                count = favoriteProductDao.retrieveFavoriteProductCount(customerId, productId)
            }
            val result = productDataSource.fetchProductDetailsById(productId)
                .asProductDetails(count > 0)
            Response.Success(result)
        } catch (throwable: Throwable) {
            Response.Error(throwable)
        }
        emit(response)
    }


    override fun fetchShopProducts(): Flow<Response<List<Product>?>> = networkBoundResource(
        query = {
            productDao.getProductsWithDiscount().map { discountAndProduct ->
                discountAndProduct.map {
                    it.asProduct()
                }
            }
        },
        fetch = {
            productDataSource.fetchShopProducts()
        },

        saveFetchResult = { products ->
            try {
                val discounts = products.map { it.networkDiscount }
                val discountEntities: MutableList<DiscountEntity> = mutableListOf()
                for (discount in discounts) {
                    if (discount != null)
                        discountEntities.add(discount.asDiscountEntity())
                }
                database.withTransaction {
                    productDao.deleteAllProducts()
                    productDao.insertProducts(products.map { it.asProductEntity() })
                    discountDao.deleteDiscounts()
                    discountDao.insertDiscounts(discountEntities)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        },

        )



    override fun searchProducts(query: String, categoryId: Int) = flow {
        emit(Response.Loading())
        val result = try {
            val response = productDataSource.fetchProductsContaining(query, categoryId)
            Response.Success(response)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }
        emit(result)
    }
}