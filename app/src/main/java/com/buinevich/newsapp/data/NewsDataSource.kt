package com.buinevich.newsapp.data

import com.buinevich.newsapp.data.local.entity.News
import io.reactivex.Single

interface NewsDataSource {

    fun changeFavouriteState(id: String): Boolean

    fun getNewsFromBD(): Single<List<News>>

    fun getStandardNews(): Single<List<News>>

    fun insertNews(news: List<News>)

    fun getNewsById(id: String): Single<News>

    fun getNewsByQuery(query: String): Single<List<News>>

    fun getTopHeadlinesByCategory(category: String): Single<List<News>>

    fun getFavourites(): Single<List<News>>

    fun deleteAllFavourites()
}
