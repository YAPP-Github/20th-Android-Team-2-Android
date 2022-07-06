package com.yapp.android2.domain.entity

import com.yapp.android2.domain.entity.base.BaseResponse
import java.time.LocalDateTime

data class LoginResponse(override val data: Data) : BaseResponse<LoginResponse.Data>() {
    data class Data(
        val accessToken: String?,
        val nickName: String?,
        val refreshToken: String?,
        val userId: Long?,
        val email: String?,
        val createAt: String?
    )
}
