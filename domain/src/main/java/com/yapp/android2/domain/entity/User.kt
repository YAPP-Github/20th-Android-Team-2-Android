package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity
import java.io.Serializable
import java.time.LocalDateTime

data class User(val userId: Long, val nickname: String, val email: String?, val createAt: String?): Entity, Serializable {

    companion object {

        val EMPTY = User(userId = 0, nickname = "", email = "", createAt = "")
    }
}
