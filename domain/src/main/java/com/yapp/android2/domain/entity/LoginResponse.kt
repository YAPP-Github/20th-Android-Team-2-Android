package com.yapp.android2.domain.entity

import com.yapp.android2.domain.entity.base.BaseResponse

data class LoginResponse(override val data: Data) : BaseResponse<LoginResponse.Data>() {
    data class Data(
        val accessToken: String?,
        val nickName: String?,
        val refreshToken: String?,
        val userId: Int?
    )
}
