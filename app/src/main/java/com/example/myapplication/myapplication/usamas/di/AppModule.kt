package com.example.myapplication.myapplication.usamas.di

import android.app.Application
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import com.example.myapplication.myapplication.usamas.data.datastore.network.DataStore
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDataStore(context: Context): DataStore {
        return DataStore(context)
    }

}
