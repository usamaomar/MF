package com.example.myapplication.myapplication.usamas.utils

import com.example.myapplication.myapplication.usamas.data.moshi.adapters.FileAdapter
import com.example.myapplication.myapplication.usamas.data.moshi.adapters.UriAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiUtils {

    inline fun <reified T> deserialize(json: String?): T? =
        try {
            if (json.isNullOrBlank()) {
                null
            } else {
                Moshi.Builder()
                    .add(UriAdapter.FACTORY)
                    .add(FileAdapter.FACTORY)
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
                    .adapter(T::class.java)
                    .fromJson(json)
            }
        } catch (ex: Exception) {
            null
        }

    inline fun <reified T> serialize(data: T): String =
        Moshi.Builder()
            .add(UriAdapter.FACTORY)
            .add(FileAdapter.FACTORY)
            .addLast(KotlinJsonAdapterFactory())
            .build()
            .adapter(T::class.java)
            .toJson(data)

    inline fun <reified T> serializeList(data: List<T>): String =
        Moshi.Builder()
            .add(UriAdapter.FACTORY)
            .add(FileAdapter.FACTORY)
            .addLast(KotlinJsonAdapterFactory())
            .build()
            .adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java))
            .toJson(data)

    inline fun <reified T> deserializeList(json: String?): List<T>? =
        try {
            if (json.isNullOrBlank()) {
                null
            } else {
                Moshi.Builder()
                    .add(UriAdapter.FACTORY)
                    .add(FileAdapter.FACTORY)
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
                    .adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java))
                    .fromJson(json)
            }
        } catch (ex: Exception) {
            null
        }
}
