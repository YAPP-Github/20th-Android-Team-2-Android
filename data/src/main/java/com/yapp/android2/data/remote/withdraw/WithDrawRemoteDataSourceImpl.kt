package com.yapp.android2.data.remote.withdraw

import com.yapp.android2.data.service.WithDrawService
import javax.inject.Inject

class WithDrawRemoteDataSourceImpl @Inject constructor(
    private val service: WithDrawService
): WithDrawRemoteDataSource {

    override suspend fun withDraw() = service.withDraw()

}
