package com.yapp.android2.domain.repository.withdraw

interface WithDrawRepository {
    suspend fun withDraw(): Result<Unit>
}
