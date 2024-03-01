package com.route.newsc39.api

import com.route.newsc39.api.model.ArticlesResponse
import com.route.newsc39.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("/v2/top-headlines/sources")
    suspend fun getSources(@Query("apiKey") apiKey: String,
                   @Query("category") categoryId: String, ): SourcesResponse

    @GET("/v2/everything")
    suspend fun getArticles(@Query("apiKey") apiKey: String,
                    @Query("sources") sourceId: String): ArticlesResponse


}