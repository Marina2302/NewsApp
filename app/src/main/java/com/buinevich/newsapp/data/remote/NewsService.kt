package com.buinevich.newsapp.data.remote

import com.buinevich.newsapp.data.remote.models.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("everything")
    fun requestNewsByQuery(
            @Query("q") query: String,
            @Query("from") from: String,
            @Query("to") to: String,
            @Query("language") language: String,
            @Query("sortBy") sortBy: String
    ): Single<NewsResponse>

    @GET("top-headlines")
    fun requestTopHeadlinesByCategory(
            @Query("category") query: String
    ): Single<NewsResponse>
}
