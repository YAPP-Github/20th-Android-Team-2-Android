package com.yapp.android2.domain.entity

import com.yapp.android2.domain.entity.base.BaseResponse

data class RenewalResponse(override val data: Data) : BaseResponse<RenewalResponse.Data>() {
    data class Data(
        val accessToken: String?
    )
}
