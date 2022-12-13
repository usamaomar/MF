package com.example.myapplication.myapplication.usamas.domain.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResponseBody<D>(
    @Json(name = "code") val code: Int?,
    @Json(name = "errors") val errors: List<Error>?,
    @Json(name = "message") val message: String?,
    @Json(name = "success") val success: Boolean?,
    @Json(name = "data") val data: D?,
)

data class ResponseBodyIo<D>(
    @Json(name = "status") val status: String?,
    @Json(name = "totalResults") val totalResults: Int?,
    @Json(name = "message") val message: String?,
    @Json(name = "errors") val errors: List<Error>?,
    @Json(name = "results") val results: D?,
)

data class ResponseBodyOrg<D>(
    @Json(name = "status") val status: String?,
    @Json(name = "totalResults") val totalResults: Int?,
    @Json(name = "message") val message: String?,
    @Json(name = "errors") val errors: List<Error>?,
    @Json(name = "articles") val articles: D?,
)

data class RawResponseBody(
    @Json(name = "code") val code: Int?,
    @Json(name = "errors") val errors: List<Error>?,
    @Json(name = "message") val message: String?,
    @Json(name = "success") val success: Boolean?,
)

data class PaginatedResponseBody<D>(
    @Json(name = "currentPage") val currentPage: Int? = 0,
    @Json(name = "totalPages") val totalPages: Int? = 0,
    @Json(name = "totalCount") val totalCount: Int? = 0,
    @Json(name = "pageSize") val pageSize: Int? = 0,
    @Json(name = "hasPreviousPage") val hasPreviousPage: Boolean? = false,
    @Json(name = "hasNextPage") val hasNextPage: Boolean? = false,
    @Json(name = "code") val code: Int,
    @Json(name = "message") val message: String?,
    @Json(name = "success") val success: Boolean,
    @Json(name = "data") var data: List<D>?,
    @Json(name = "errors") val errors: List<Error>?
)

data class Error(
    @Json(name = "code") val code: Int,
    @Json(name = "field") val field: String?,
    @Json(name = "message") val message: String?,
)

fun ResponseBodyOrg<*>.message(): String? {
    return if (errors.isNullOrEmpty()) message else errors.first().message ?: message
}

fun ResponseBody<*>.message(): String? {
    return if (errors.isNullOrEmpty()) message else errors.first().message ?: message
}

fun RawResponseBody.message(): String? {
    return if (errors.isNullOrEmpty()) message else errors.first().message ?: message
}