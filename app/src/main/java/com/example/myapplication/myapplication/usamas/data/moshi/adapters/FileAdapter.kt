package com.example.myapplication.myapplication.usamas.data.moshi.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonAdapter.Factory
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.File
import java.io.IOException

class FileAdapter : JsonAdapter<File>() {

    companion object {
        val FACTORY = Factory { type, _, _ ->
            return@Factory if (type === File::class.java) {
                FileAdapter()
            } else {
                null
            }
        }
    }

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): File {
        return File(reader.nextString())
    }

    @Throws(IOException::class)
    override fun toJson(
        writer: JsonWriter,
        value: File?
    ) {
        writer.value(value?.toString())
    }

    override fun toString(): String {
        return "JsonAdapter(File)"
    }
}