package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity
import java.io.Serializable

data class User(val userId: Long, val nickname: String): Entity, Serializable {

    companion object {

        val EMPTY = User(userId = 0, nickname = "")
    }
}
