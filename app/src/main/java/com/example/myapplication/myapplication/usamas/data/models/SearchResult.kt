package com.example.myapplication.myapplication.usamas.data.models


data class SearchResult(
    val type : String?="",
    val title: String? = "",
    val imageUrl: String? = "",
    val author: String? = "",
    val postDate: String? = "",
    val url: String? = "",
    val country: List<String>? = emptyList(),
    val category: List<String>? = emptyList(),
)
