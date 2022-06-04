package com.best.friends.login.util

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun UserApiClient.Companion.loginWithKakao(
    context: Context,
    callback: () -> Unit
): OAuthToken {
    return if (instance.isKakaoTalkLoginAvailable(context)) {
        try {
            UserApiClient.loginWithKakaoTalk(context, callback)
        } catch (error: Throwable) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) throw error

            UserApiClient.loginWithKakaoAccount(context, callback)
        }
    } else {
        UserApiClient.loginWithKakaoAccount(context, callback)
    }
}

suspend fun UserApiClient.Companion.loginWithKakaoTalk(
    context: Context,
    callback: () -> Unit
): OAuthToken {
    return suspendCoroutine { continuation ->
        instance.loginWithKakaoTalk(context) { token, error ->
            when {
                error != null -> {
                    continuation.resumeWithException(error)
                }
                token != null -> {
                    callback()
                    continuation.resume(token)
                }
                else -> {
                    continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
                }
            }
        }
    }
}

private suspend fun UserApiClient.Companion.loginWithKakaoAccount(
    context: Context,
    callback: () -> Unit
): OAuthToken {
    return suspendCoroutine { continuation ->
        instance.loginWithKakaoAccount(context) { token, error ->
            when {
                error != null -> {
                    continuation.resumeWithException(error)
                }
                token != null -> {
                    callback()
                    continuation.resume(token)
                }
                else -> {
                    continuation.resumeWithException(RuntimeException("kakao access token을 받아오는데 실패함, 이유는 명확하지 않음."))
                }
            }
        }
    }
}
