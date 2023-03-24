package com.ars.domain.model

import java.math.BigDecimal

data class Location(
    val id: Int,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)
