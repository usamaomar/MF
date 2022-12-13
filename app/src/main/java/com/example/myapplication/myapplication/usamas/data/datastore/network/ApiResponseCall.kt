package com.example.myapplication.myapplication.usamas.data.datastore.network


import com.example.myapplication.myapplication.usamas.network.ApiResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class ApiResponseCall<R>(private val delegate: Call<R>, private val responseType: Type) :
    Call<ApiResponse<R>> {

    override fun enqueue(callback: Callback<ApiResponse<R>>) =
        delegate.enqueue(object : Callback<R> {
            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse.create(response))
                )
            }

            override fun onFailure(call: Call<R>, t: Throwable) {
                callback.onResponse(
                    this@ApiResponseCall,
                    Response.success(ApiResponse.create(t))
                )
            }

        })

    override fun clone(): Call<ApiResponse<R>> = ApiResponseCall(delegate.clone(), responseType)

    override fun execute(): Response<ApiResponse<R>> {
        return try {
            val response = delegate.execute()
            Response.success(ApiResponse.create(response))
        } catch (ex: Exception) {
            Response.success(ApiResponse.create(ex))
        }
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}