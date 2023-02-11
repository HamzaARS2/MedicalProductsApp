package com.ars.domain.repository

import com.ars.domain.utils.Resource

interface ICRUDRepository<T,R> {
    suspend fun insert(data: T): Resource<T>
    suspend fun retrieve(id: R): Resource<T>
    suspend fun retrieveAll(): Resource<List<T>>
    suspend fun update(data: T): Resource<T>
    suspend fun delete(id: R): Resource<String>
}