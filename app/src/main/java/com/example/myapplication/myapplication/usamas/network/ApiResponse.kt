package com.example.myapplication.myapplication.usamas.network

import com.example.myapplication.myapplication.usamas.domain.entities.*
import com.example.myapplication.myapplication.usamas.utils.MoshiUtils
import retrofit2.Response

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(
                RetrofitException(
                    message = error.message ?: "Something went wrong",
                    statusCode = StatusCode.Unknown,
                    ServerError.Unknown
                )
            )
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body = body)
                }
            } else {
                ApiErrorResponse(response.extractRetrofitException())
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

data class ApiErrorResponse<T>(val error: RetrofitException? = null) : ApiResponse<T>()

/**
 * HTTP response body converted to specified `type`. `null` if there is no
 * response.
 *
 */
inline fun <reified T> okhttp3.ResponseBody.getErrorBody(): T? {
    return this.string().getErrorBody()
}

inline fun <reified T> String.getErrorBody(): T? {
    return try {
        MoshiUtils.deserialize(this)
    } catch (ex: Exception) {
        null
    }
}


fun <T> Response<T>.extractRetrofitException(): RetrofitException {

    val statusCode = StatusCode.fromCode(code())

    return try {
        errorBody()?.getErrorBody<RawResponseBody>()
            ?.let { body ->
                val serverError: ServerError = getServerError(statusCode, body)

                val errorMsg = if (body.message().isNullOrBlank().not())
                    body.message()
                else if (errorBody()?.string().isNullOrEmpty()) {
                    message() ?: "Something went wrong"
                } else {
                    errorBody()?.string()
                }

                RetrofitException(
                    message = errorMsg ?: "Something went wrong",
                    statusCode = statusCode,
                    error = serverError
                )

            } ?: run {
            RetrofitException(
                message = "Something went wrong",
                statusCode = statusCode,
                getServerError(statusCode)
            )
        }

    } catch (ex: Exception) {
        val errorMessage = ex.localizedMessage ?: "Something went wrong"
        RetrofitException(
            message = errorMessage,
            statusCode = statusCode,
            getServerError(statusCode)
        )
    }
}

fun okhttp3.Response.extractRetrofitException(): RetrofitException {

    val statusCode = StatusCode.fromCode(code)

    return try {

        val mBody = peekBody(2048).string()

        mBody.getErrorBody<RawResponseBody>()
            ?.let { body ->
                val serverError: ServerError = getServerError(statusCode, body)

                val errorMsg = if (body.message().isNullOrBlank().not())
                    body.message()
                else if (mBody.isEmpty()) {
                    message
                } else {
                    mBody
                }

                RetrofitException(
                    message = errorMsg ?: "Something went wrong",
                    statusCode = statusCode,
                    error = serverError
                )

            } ?: run {
            RetrofitException(
                message = "Something went wrong",
                statusCode = statusCode,
                getServerError(statusCode)
            )
        }

    } catch (ex: Exception) {
        val errorMessage = ex.localizedMessage ?: "Something went wrong"
        RetrofitException(
            message = errorMessage,
            statusCode = statusCode,
            getServerError(statusCode)
        )
    }
}

private fun getServerError(
    statusCode: StatusCode,
    body: RawResponseBody? = null
): ServerError {
    val serverError: ServerError = when (statusCode) {
        StatusCode.BadRequest -> {
            ServerError.BadRequest(body?.code?.let { BadRequestCode.fromCode(it) }
                ?: BadRequestCode.Unknown)
        }
        StatusCode.Conflict -> {
            ServerError.Conflict
        }
        StatusCode.UnprocessableEntity -> {
            ServerError.UnprocessableEntity
        }
        StatusCode.TooManyRequests -> {
            ServerError.TooManyRequests
        }
        StatusCode.InternalServerError -> {
            ServerError.InternalServerError
        }
        StatusCode.ServiceUnavailable -> {
            ServerError.ServiceUnavailable
        }
        StatusCode.Unauthorized -> {
            ServerError.Unauthorized(body?.code?.let { UnauthorizedCode.fromCode(it) }
                ?: UnauthorizedCode.Unknown)
        }
        StatusCode.Forbidden -> {
            ServerError.Forbidden(body?.code?.let { ForbiddenCode.fromCode(it) }
                ?: ForbiddenCode.Unknown)
        }
        else -> {
            ServerError.Unknown
        }
    }
    return serverError
}