package com.example.data.api

import com.example.data.model.GuardianResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GuardianApiService {
    @GET("search")
    suspend fun getArticles(
        @Query("api-key") apiKey: String,
        @Query("show-fields") showFields: String = "body,thumbnail,headline,trailText,byline",
        @Query("section") section: String? = null,
        @Query("q") query: String? = null,
        @Query("page-size") pageSize: Int = 30
    ): GuardianResponse
}
