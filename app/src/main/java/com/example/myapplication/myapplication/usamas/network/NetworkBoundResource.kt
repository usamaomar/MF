package com.example.myapplication.myapplication.usamas.network


import com.example.myapplication.myapplication.usamas.domain.entities.RetrofitException
import com.example.myapplication.myapplication.usamas.domain.entities.ServerError
import com.example.myapplication.myapplication.usamas.domain.entities.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext



inline fun <RequestType> networkApiCall(
    crossinline createCall: suspend () -> ApiResponse<RequestType>,
    crossinline saveCallResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: () -> Unit = {},
    crossinline preCall: () -> Unit = {}
) = flow {
    emit(Resource.Loading())

    withContext(Dispatchers.IO) {
        preCall()
    }

    try {
        when (val apiResponse = createCall()) {
            is ApiSuccessResponse -> {
                val item = apiResponse.body
                withContext(Dispatchers.IO) {
                    saveCallResult(item)
                }
                emit(Resource.Success(item))
            }
            is ApiEmptyResponse -> {
                emit(Resource.Success(null))
            }
            is ApiErrorResponse -> {
                onFetchFailed()
                emit(Resource.Error(apiResponse.error, null))
            }
        }
    } catch (error: Throwable) {
        emit(
            Resource.Error(
                RetrofitException(
                    error.localizedMessage ?: error.message ?: "",
                    StatusCode.Unknown,
                    ServerError.Unknown
                )
            )
        )
    }
}