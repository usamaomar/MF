package com.example.myapplication.myapplication.usamas.di

import com.example.myapplication.myapplication.usamas.data.repositories.BusinessDataRepoImpl
import com.example.myapplication.myapplication.usamas.data.repositories.BusinessRepoImpl
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessDataRepo
import com.example.myapplication.myapplication.usamas.domain.repositories.BusinessRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoriesModule {
    @Binds
    abstract fun bindBusinessRepo(repoImpl: BusinessRepoImpl): BusinessRepo

    @Binds
    abstract fun bindBusinessDataRepo(repoImpl: BusinessDataRepoImpl): BusinessDataRepo
}