package com.ars.data.repository

import android.util.Log
import com.ars.data.extensions.asAddress
import com.ars.data.extensions.asNetworkAddress
import com.ars.data.network.api.AddressApi
import com.ars.domain.model.Address
import com.ars.domain.utils.Resource
import com.ars.domain.utils.Response
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepository @Inject constructor(
    private val addressApi: AddressApi
) {

    fun saveAddress(address: Address, customerId: String) = flow {
        emit(Response.Loading())
        val result = try {
            val networkAddress = address.asNetworkAddress()
            Log.d("AddressRepository", "saveAddress: id is = " + address.id)
            val response = if (address.id != null) addressApi.updateAddress(networkAddress)
            else addressApi.createAddress(networkAddress, customerId)
            Response.Success(response.asAddress())
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Response.Error(throwable)
        }

        emit(result)
    }




}