package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity
import java.io.Serializable
import java.lang.Exception
import java.text.DecimalFormat

data class Product(
    val productId: Long,
    val name: String?,
    val price: String,
    val resolution: String?,
    val today: String?,
    val checked: Boolean,
    val accmTimes: Int,
    val totalTimes: Int
) : Entity, Serializable {

    val formattedPrice: String
        get() = try {
            DecimalFormat("###,###").format(price.toInt())
        } catch (e: Exception) {
            price
        }

    val wonPrice: String
        get() = try {
            val formatText = DecimalFormat("###,###").format(price.toInt())
            String.format("%s원", formatText)
        } catch (e: Exception) {
            price
        }

    val totalTimesFormat: String
        get() = try {
            String.format("%d번 중", totalTimes)
        } catch (e: Exception) {
            totalTimes.toString()
        }

    val accmTimesFormat: String
        get() = try {
            String.format("%d번", accmTimes)
        } catch (e: Exception) {
            accmTimes.toString()
        }
}
