package com.example.myapplication.myapplication.usamas.di

import com.example.myapplication.myapplication.usamas.data.datastore.network.ApiResponseCallAdapterFactory
import com.example.myapplication.myapplication.usamas.data.datastore.network.CurrentNetworkPreferences
import com.example.myapplication.myapplication.usamas.data.datastore.network.NetworkPreferences
import com.example.myapplication.myapplication.usamas.network.NewsUrlFactory
import com.example.myapplication.myapplication.usamas.network.UrlFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideCurrentNetworkPreferences(networkPreferences: NetworkPreferences): CurrentNetworkPreferences {
        return networkPreferences.currentNetworkPreferences
    }

    @Provides
    fun provideUrlFactory(networkPreferences: CurrentNetworkPreferences): UrlFactory {
        return NewsUrlFactory(
            environment = networkPreferences.environment,
            protocol = networkPreferences.protocol
        )
    }

    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org")
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }
}