package com.example.myapplication.myapplication.usamas.di

import com.example.myapplication.myapplication.usamas.domain.use_cases.business.GetDataUseCase
import com.example.myapplication.myapplication.usamas.domain.use_cases.business.GetDataUseCaseImpl
import com.example.myapplication.myapplication.usamas.domain.use_cases.business.GetNewsUseCase
import com.example.myapplication.myapplication.usamas.domain.use_cases.business.GetNewsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class UseCasesModule {
    @Binds
    abstract fun bindGetNewsUseCase(repoImpl: GetNewsUseCaseImpl): GetNewsUseCase

    @Binds
    abstract fun bindGetDataUseCase(repoImpl: GetDataUseCaseImpl): GetDataUseCase
}