package com.example.myapplication.myapplication.usamas.data.datastore.network

import com.example.myapplication.myapplication.usamas.network.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Call<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Call<ApiResponse<R>> = ApiResponseCall(call, responseType)

}