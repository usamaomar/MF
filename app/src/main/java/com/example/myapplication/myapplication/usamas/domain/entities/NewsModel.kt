package com.example.myapplication.myapplication.usamas.domain.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsModel(
    @Json(name = "source") val source: SourceModel? = null,
    @Json(name = "author") val author: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "urlToImage") val urlToImage: String? = null,
    @Json(name = "publishedAt") val publishedAt: String? = null,
    @Json(name = "content") val content: String? = null,
) : Parcelable


@Parcelize
data class SourceModel(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
) : Parcelable