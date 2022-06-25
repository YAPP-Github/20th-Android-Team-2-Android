package com.yapp.android2.domain.entity

import com.yapp.android2.domain.entity.base.BaseResponse

data class NotificationResponse(override val data: Data) : BaseResponse<NotificationResponse.Data>() {
    data class Data(
        val title: String?,
        val body: String?
    )
}
