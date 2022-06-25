package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity

data class Product(
    val productId: Long,
    val name: String?,
    val price: String?,
    val resolution: String?,
    val today: String?,
    val checked: Boolean?
) : Entity
