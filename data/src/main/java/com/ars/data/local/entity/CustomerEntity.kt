package com.ars.data.local.entity

import androidx.room.PrimaryKey

data class CustomerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val phone: String?,
    val streetInfo: String?,

    )