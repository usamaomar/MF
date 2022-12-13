package com.example.myapplication.myapplication.usamas.di

import com.example.myapplication.myapplication.usamas.network.services.BusinessDataService
import com.example.myapplication.myapplication.usamas.network.services.BusinessService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object RetrofitServicesModule {

    @Provides
    fun provideBusinessService(retrofit: Retrofit): BusinessService {
        return retrofit.create(BusinessService::class.java)
    }

    @Provides
    fun provideBusinessDataService(retrofit: Retrofit): BusinessDataService {
        return retrofit.create(BusinessDataService::class.java)
    }

}