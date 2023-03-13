package com.ars.data.repository.product

import androidx.room.withTransaction
import com.ars.data.extensions.asDiscountEntity
import com.ars.data.extensions.asProduct
import com.ars.data.extensions.asProductEntity
import com.ars.data.local.GroceriesDatabase
import com.ars.data.local.entity.DiscountEntity
import com.ars.data.network.ProductDataSource
import com.ars.data.util.networkBoundResource
import com.ars.domain.model.Product
import com.ars.domain.utils.Resource
import com.ars.domain.model.ProductDetails
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

    override suspend fun retrieve(id: Int): Resource<ProductDetails> {
        return try {
            val response = productDataSource.fetchProductDetailsById(id)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
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


    override suspend fun searchProducts(query: String): Resource<List<Product>> {
        return try {
            val response = productDataSource.fetchProductsContaining(query)
            Resource.Success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}