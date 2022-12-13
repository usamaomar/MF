package com.example.myapplication.myapplication.usamas.domain.entities

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsDataModel(
    @Json(name = "title") val title: String? = null,
    @Json(name = "author") val author: String? = null,
    @Json(name = "link") val link: String? = null,
    @Json(name = "keywords") val keywords: List<String>? = emptyList(),
    @Json(name = "creator") val creator: List<String>? = emptyList(),
    @Json(name = "video_url") val videoUrl: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "pubDate") val pupDate: String? = null,
    @Json(name = "full_description") val fullDescription: String? = null,
    @Json(name = "content") val content: String? = null,
    @Json(name = "image_Url") val imageUrl: String? = null,
    @Json(name = "source_id") val sourceId: String? = null,
    @Json(name = "country") val country: List<String>? = emptyList(),
    @Json(name = "category") val category: List<String>? = emptyList(),
    @Json(name = "language") val language: String? = null
) : Parcelable

