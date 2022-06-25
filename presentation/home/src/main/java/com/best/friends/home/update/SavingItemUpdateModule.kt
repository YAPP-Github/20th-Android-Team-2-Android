package com.best.friends.home.update

import androidx.lifecycle.SavedStateHandle
import com.best.friends.home.update.SavingItemUpdateActivity.Companion.EXTRA_PRODUCT
import com.yapp.android2.domain.entity.Product
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SavingItemUpdateModule {

    @Provides
    @ViewModelScoped
    fun provideParams(handle: SavedStateHandle): SavingItemUpdateViewModel.Params {
        val product = handle.get<Product>(EXTRA_PRODUCT)
            ?: throw IllegalStateException("product must be not null")

        return SavingItemUpdateViewModel.Params(product)
    }
}
