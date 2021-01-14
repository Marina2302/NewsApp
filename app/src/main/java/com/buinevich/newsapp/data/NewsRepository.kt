package com.buinevich.newsapp.data

import com.buinevich.newsapp.data.local.NewsLocalDataSource
import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.data.remote.RemoteDataSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class NewsRepository(private val local: NewsLocalDataSource, private val remote: RemoteDataSource) : NewsDataSource {

    override fun changeFavouriteState(id: String): Boolean {
        return local.changeFavouriteState(id)
    }

    override fun getNewsFromBD(): Single<List<News>> {
        return local.getNews()
    }

    override fun getStandardNews(): Single<List<News>> {
        val loadedListNews: Single<List<News>> = remote.getStandardNews()
        loadedListNews
                .subscribeOn(Schedulers.io())
                .subscribe { news -> insertNews(news) }
        return loadedListNews
    }

    override fun insertNews(news: List<News>) {
        local.insertNews(news)
    }

    override fun getNewsById(id: String): Single<News> {
        return local.getNewsById(id)
    }

    override fun getNewsByQuery(query: String): Single<List<News>> {
        return remote.getNewsByQuery(query)
    }

    override fun getTopHeadlinesByCategory(category: String): Single<List<News>> {
        return remote.getTopHeadlinesByCategory(category)
    }

    override fun getFavourites(): Single<List<News>> {
        return local.getFavourites()
    }

    override fun deleteAllFavourites() {
        local.deleteAllFavourites()
    }
}
