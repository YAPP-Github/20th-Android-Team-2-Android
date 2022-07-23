package com.yapp.android2.domain.entity

import com.yapp.android2.domain.Entity
import java.io.Serializable

data class Notification(
    val title: String?,
    val body: String?,
    val elapsedTime: String?,
    val createAt: String?
): Entity, Serializable {

    companion object {
        const val INIT_TIME = "0000-00-00T00:00:00"
    }

}

