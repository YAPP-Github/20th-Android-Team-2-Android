package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity
import java.io.Serializable

data class User(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val nickName: String,
    val email: String,
    val createAt: String
) : Entity, Serializable {

    companion object {

        val EMPTY = User(
            accessToken = "",
            refreshToken = "",
            userId = 0,
            nickName = "",
            email = "",
            createAt = ""
        )
    }
}
