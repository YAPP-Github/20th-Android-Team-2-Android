package com.yapp.android2.data.remote.request

data class PostLoginRequest(
    val email: String,
    val nickName: String,
    val provider: String,
    val providerId: String
)
