package com.ars.domain.utils

sealed class AuthResource<out T> {
    data class Success<out T>(val result: T): AuthResource<T>()
    data class Failure(val e: Exception): AuthResource<Nothing>()
    data class InvalidCredentials(val response: Validation.RegisterResponse): AuthResource<Nothing>()
    object Loading: AuthResource<Nothing>()
}