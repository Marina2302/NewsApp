package com.buinevich.newsapp.data.remote

import com.buinevich.newsapp.data.local.entity.News
import io.reactivex.Single

interface RemoteDataSource {
    fun getStandardNews(): Single<List<News>>

    fun getNewsByQuery(query: String): Single<List<News>>

    fun getTopHeadlinesByCategory(category: String): Single<List<News>>
}
