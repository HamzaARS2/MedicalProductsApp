package com.ars.domain.utils

data class ValidationResponse(
    val isValid: Boolean,
    val message: String? = null
)
