package com.example.myapplication.myapplication.usamas.network.services


import com.example.myapplication.myapplication.usamas.domain.entities.NewsModel
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBody
import com.example.myapplication.myapplication.usamas.domain.entities.ResponseBodyOrg
import com.example.myapplication.myapplication.usamas.network.*
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface BusinessService {
    @GET
    suspend fun getNewsApi(
        @Url url: String,
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
    ): ApiResponse<ResponseBodyOrg<List<NewsModel>>>

}

enum class BusinessAPI : NewsAPI {
    Everything,
    News;

    override fun path(): String = when (this) {
        Everything -> "everything"
        News -> "news"
    }

    override fun environment(): Environment = Environment.OrgDomain

    override fun scope(): Scope = Scope.Newsapi

    override fun version(): Version = Version.V2
}