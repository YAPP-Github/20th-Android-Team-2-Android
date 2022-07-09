package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity
import java.io.Serializable

data class RecentNotification(
    val createAt: String?
): Entity, Serializable {

    companion object {

        val EMPTY = RecentNotification(
            createAt = "0000-00-00T00:00:00"
        )
    }
}
