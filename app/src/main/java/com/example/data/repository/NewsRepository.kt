package com.example.data.repository

import com.example.data.api.GuardianApiService
import com.example.data.model.GuardianResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsRepository {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        })
        .build()

    private val apiService = Retrofit.Builder()
        .baseUrl("https://content.guardianapis.com/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(GuardianApiService::class.java)

    suspend fun fetchArticles(
        apiKey: String,
        section: String? = null,
        query: String? = null
    ): GuardianResponse {
        val key = apiKey.trim().ifBlank { "c4c33472-1205-4c46-b16a-9cc0a2989551" }
        // The Guardian sections:
        // World: 'world'
        // Technology: 'technology'
        // Business: 'business'
        // Home (general query / latest): section = null
        return apiService.getArticles(
            apiKey = key,
            section = section,
            query = query
        )
    }
}
