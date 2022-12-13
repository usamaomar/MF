package com.example.myapplication.myapplication.usamas.data.models

data class RequestNewsOrg(
    val query: String? = "",
    val from: String? = "",
    val sortBy: String? = "",
    val apiKey: String? = "",
)