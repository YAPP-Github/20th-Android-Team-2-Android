package com.yapp.android2.domain.entity

import com.yapp.android2.domain.entity.base.BaseResponse

data class Notification(override val data: Data) : BaseResponse<Notification.Data>() {
    data class Data(
        val title: String?,
        val body: String?
    )
}
