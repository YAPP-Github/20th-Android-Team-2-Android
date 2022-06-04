package com.yapp.android2.domain.entity

import com.yapp.android2.domain.entity.base.BaseResponse

data class LoginResponse(override val data: String) : BaseResponse<String>()
