package com.example.myapplication.myapplication.usamas.data.datastore.network

import com.example.myapplication.myapplication.usamas.network.Environment
import com.example.myapplication.myapplication.usamas.network.Protocol
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@DataStore.Key("CurrentNetworkPreferences")
data class CurrentNetworkPreferences(val environment: Environment, val protocol: Protocol)