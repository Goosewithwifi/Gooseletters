package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GuardianResponse(
    @Json(name = "response") val response: ResponseData
)

@JsonClass(generateAdapter = true)
data class ResponseData(
    @Json(name = "status") val status: String,
    @Json(name = "total") val total: Int,
    @Json(name = "results") val results: List<NewsArticle>
)

@JsonClass(generateAdapter = true)
data class NewsArticle(
    @Json(name = "id") val id: String,
    @Json(name = "sectionId") val sectionId: String,
    @Json(name = "sectionName") val sectionName: String,
    @Json(name = "webPublicationDate") val webPublicationDate: String,
    @Json(name = "webTitle") val webTitle: String,
    @Json(name = "webUrl") val webUrl: String,
    @Json(name = "fields") val fields: ArticleFields?
)

@JsonClass(generateAdapter = true)
data class ArticleFields(
    @Json(name = "headline") val headline: String?,
    @Json(name = "body") val body: String?,
    @Json(name = "thumbnail") val thumbnail: String?,
    @Json(name = "trailText") val trailText: String?,
    @Json(name = "byline") val byline: String?
)
