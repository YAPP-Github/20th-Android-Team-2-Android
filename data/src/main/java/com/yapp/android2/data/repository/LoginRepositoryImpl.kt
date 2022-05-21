package com.yapp.android2.data.repository

import com.yapp.android2.data.remote.login.LoginRemoteDataSource
import com.yapp.android2.domain.entity.Login
import com.yapp.android2.domain.entity.base.ApiResponse
import com.yapp.android2.domain.repository.login.LoginRepository
import com.yapp.android2.domain.usecase.LoginType
import retrofit2.HttpException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource
): LoginRepository {
    override suspend fun login(loginType: LoginType): ApiResponse<Login> {
        return try {
            loginRemoteDataSource.login(loginType)
        } catch (exception: Exception) {
            ApiResponse(data = Login.Error, statusCode = 404, message = exception.message.orEmpty())
        } catch (exception: HttpException) {
            ApiResponse(data = Login.Error, statusCode = exception.code(), message = exception.message())
        }
    }
}
