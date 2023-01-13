package com.reddevx.groceriesapp.data.network.repository

import com.reddevx.groceriesapp.data.network.Resource

interface IFetchRepository<H,U> {
    suspend fun retrieve(id: U): Resource<H?>
    suspend fun retrieveAll(): Resource<List<H>?>
}