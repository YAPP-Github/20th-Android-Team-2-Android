package com.yapp.android2.domain.entity

data class Login(val redirectUrl: String) {
    companion object {
        val Error = Login("")
    }

    enum class Type { KAKAO }
}
