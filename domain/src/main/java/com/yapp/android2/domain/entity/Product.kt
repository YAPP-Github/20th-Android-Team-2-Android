package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity
import java.io.Serializable
import java.lang.Exception
import java.text.DecimalFormat

data class Product(
    val productId: Long,
    val name: String?,
    val price: String?,
    val resolution: String?,
    val today: String?,
    val checked: Boolean?
) : Entity, Serializable {

    val formattedPrice: String
        get() = try {
            DecimalFormat("###,###").format(price?.toInt())
        } catch (e: Exception) {
            price.toString()
        }

    val wonPrice: String
        get() = try {
            val formatText = DecimalFormat("###,###").format(price?.toInt())
            String.format("%s원", formatText)
        } catch (e: Exception) {
            price.toString()
        }
}
