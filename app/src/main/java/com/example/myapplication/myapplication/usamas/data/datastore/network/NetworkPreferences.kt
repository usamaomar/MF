package com.example.myapplication.myapplication.usamas.data.datastore.network

import com.example.myapplication.myapplication.usamas.BuildConfig
import com.example.myapplication.myapplication.usamas.network.Environment
import com.example.myapplication.myapplication.usamas.network.Protocol
import com.example.myapplication.myapplication.usamas.utils.MoshiUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkPreferences @Inject constructor(private val dataStore: DataStore) {
    var currentNetworkPreferences: CurrentNetworkPreferences = getNetworkPreferences()
        private set

    fun savePreferences(preferences: CurrentNetworkPreferences) {
        dataStore.save(preferences, MoshiUtils::serialize)
        this.currentNetworkPreferences = preferences
    }

    private fun getNetworkPreferences(): CurrentNetworkPreferences {
        return dataStore.restore(CurrentNetworkPreferences::class.java, MoshiUtils::deserialize)
            ?: getDefaultNetworkPreferences(true)
    }

    private fun getDefaultNetworkPreferences(debug: Boolean): CurrentNetworkPreferences =
        CurrentNetworkPreferences(
            environment = Environment.IoDomain ,
            protocol = Protocol.HTTPS
        )
}