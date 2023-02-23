package com.ars.domain.validation

data class ValidationResponse(
    val isValid: Boolean,
    val message: String? = null
)
