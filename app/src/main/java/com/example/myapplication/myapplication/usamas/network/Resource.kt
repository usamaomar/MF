package com.example.myapplication.myapplication.usamas.network

import com.example.myapplication.myapplication.usamas.domain.entities.RetrofitException

sealed class Resource<out T>(
    open val data: T? = null,
    val message: String? = null
) {
    data class Success<out T>(override val data: T? = null) : Resource<T>(data)
    data class Loading<out T>(override val data: T? = null) : Resource<T>(data)
    data class Error<out T>(
        val error: RetrofitException? = null,
        override val data: T? = null
    ) :
        Resource<T>(data, error?.message ?: "")

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Success<*> -> {
                if (this !is Success) return false
                checkData(other)
            }
            is Loading<*> -> {
                if (this !is Loading) return false
                checkData(other)
            }
            is Error<*> -> {
                if (this !is Error) return false
                checkData(other)
            }
            else -> super.equals(other)
        }
    }

    private fun checkData(other: Resource<*>) =
        if (data is Array<*> && other.data is Array<*>)
            (data as Array<*>).contentEquals(other.data as Array<*>)
        else
            data?.equals(other.data) ?: run {
                if (this is Error && other is Error) error == other.error else false
            }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }
}