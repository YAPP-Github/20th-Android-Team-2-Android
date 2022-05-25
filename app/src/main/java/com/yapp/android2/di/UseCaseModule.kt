package com.yapp.android2.di

import com.yapp.android2.domain.usecase.LoginUseCase
import com.yapp.android2.domain.usecase.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsLoginUseCase(useCase: LoginUseCaseImpl) : LoginUseCase
}
