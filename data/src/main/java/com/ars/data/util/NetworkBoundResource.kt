package com.ars.data.util

import com.ars.domain.model.Category
import com.ars.domain.model.Product
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.*


inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val response = if (shouldFetch(data)) {
        emit(Response.Loading(data))

        try {
            // Make a network call
            val resultType = fetch()
            // Save network response in local db
            saveFetchResult(resultType)
            // Read data from local db
            query().map { Response.Success(it) }
        } catch (throwable: Throwable) {
            // Read data from local db when we can't make a network call
            query().map { Response.Error(throwable, it) }
        }
    } else {
        // Just read data from local db when we don't need to make a network call
        query().map { Response.Success(it) }
    }
    // Finally, emit the response we got
    emitAll(response)
}


fun getCategoriesUseCase(): Flow<Response<List<Category>>>? = null
fun getProductsUseCase(): Flow<Response<List<Product>>>? = null

fun food() {
    val combinedFlow = getProductsUseCase()!!.combine(getCategoriesUseCase()!!) { products, categories ->
        Pair(products,categories)
    }



}




































