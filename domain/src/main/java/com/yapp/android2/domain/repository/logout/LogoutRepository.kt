package com.yapp.android2.domain.repository.logout

interface LogoutRepository {
    suspend fun logout(): Result<Unit>
}
