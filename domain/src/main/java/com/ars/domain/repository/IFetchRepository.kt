package com.ars.domain.repository

import com.ars.domain.Resource

interface IFetchRepository<H,U> {
    suspend fun retrieve(id: U): Resource<H>
    suspend fun retrieveAll(): Resource<List<H>?>
}