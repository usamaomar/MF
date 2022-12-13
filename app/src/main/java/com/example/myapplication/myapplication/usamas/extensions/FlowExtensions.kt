package com.example.myapplication.myapplication.usamas.extensions

import com.example.myapplication.myapplication.usamas.domain.entities.asRetrofitException
import com.example.myapplication.myapplication.usamas.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip

/**
 * Returns a flow containing the results of applying the given [transform] function to each value of the original flow.
 */
inline fun <T, R> Flow<T?>.mapNullable(crossinline transform: suspend (value: T?) -> R?): Flow<R?> =
    transform { value ->
        return@transform emit(transform(value))
    }

fun <T1 : Resource<R1>, T2 : Resource<R2>, R1, R2, R3> Flow<T1>.zipResource(
    other: Flow<T2>,
    transform: suspend (T1, T2) -> Resource.Success<R3>
): Flow<Resource<R3>> = zip(other) { resourceOne, resourceTwo ->
    when {
        resourceOne is Resource.Loading<*> || resourceTwo is Resource.Loading<*> -> Resource.Loading()
        resourceOne is Resource.Error<*> -> Resource.Error(resourceOne.error)
        resourceTwo is Resource.Error<*> -> Resource.Error(resourceTwo.error)
        resourceOne is Resource.Success<*> && resourceTwo is Resource.Success<*> -> transform(
            resourceOne,
            resourceTwo
        )
        else -> Resource.Error(IllegalStateException("Unexpected state got ${resourceOne.javaClass.name} and ${resourceTwo.javaClass.name}").asRetrofitException())
    }
}

fun <T1 : Resource<R1>, R1, R2> Flow<T1>.mapResource(transform: suspend (R1?) -> R2?): Flow<Resource<R2>> {
    return mapNotNull { resource ->
        val data = transform(resource.data)
        when (resource) {
            is Resource.Error<*> -> Resource.Error(resource.error, data)
            is Resource.Loading<*> -> Resource.Loading(data)
            is Resource.Success<*> -> Resource.Success(data)
            else -> throw IllegalStateException("Resource unknown")
        }
    }
}