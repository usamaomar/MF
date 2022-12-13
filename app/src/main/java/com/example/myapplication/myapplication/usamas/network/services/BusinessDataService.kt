package com.example.myapplication.myapplication.usamas.network.services


import com.example.myapplication.myapplication.usamas.domain.entities.*
import com.example.myapplication.myapplication.usamas.network.*
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface BusinessDataService {
    @GET
    suspend fun getDataApi(
        @Url url: String,
        @Query("q") query: String,
        @Query("apiKey") apiKey: String,
    ): ApiResponse<ResponseBodyIo<List<NewsDataModel>>>
}

enum class BusinessDataAPI : NewsAPI {
    News;

    override fun path(): String = when (this) {
        News -> "news"
    }

    override fun environment(): Environment = Environment.IoDomain

    override fun scope(): Scope = Scope.NewsData

    override fun version(): Version = Version.V1
}