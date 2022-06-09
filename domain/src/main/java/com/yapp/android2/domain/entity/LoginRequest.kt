package com.yapp.android2.domain.entity

data class LoginRequest(
    val email: String,
    val nickName: String,
    val providerId: Long
)