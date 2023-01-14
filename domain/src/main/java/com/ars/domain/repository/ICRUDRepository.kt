package com.ars.domain.repository

interface ICRUDRepository<T,R> {
    suspend fun insert(data: T): T
    suspend fun retrieve(id: R): T?
    suspend fun retrieveAll(): List<T>
    suspend fun update(id: R, data: T): T
    suspend fun delete(id: R): String
}