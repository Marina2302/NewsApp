package com.buinevich.newsapp.data.local

import com.buinevich.newsapp.data.local.entity.News
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsLocalDataSource(private val database: NewsDatabase) {

    fun changeFavouriteState(id: String): Boolean {
        var state = false
        database.newsDao().getNewsById(id)
                .subscribeOn(Schedulers.io())
                .subscribe { news ->
                    news.isFavourite = !news.isFavourite
                    state = news.isFavourite
                    database.newsDao().updateFavouriteState(news)
                }
        return state
    }

    fun getNews(): Single<List<News>> {
        return database.newsDao().getNews()
    }

    fun getNewsById(id: String): Single<News> {
        return database.newsDao().getNewsById(id)
    }

    fun getFavourites(): Single<List<News>> {
        return database.newsDao().getFavorites(true)
    }

    fun insertNews(news: List<News>) {
        GlobalScope.launch {
            val favoriteNewsIds = database.newsDao().getFavoritesNews().map { fav -> fav.id }
            database.newsDao().insertAll(news.filter { !favoriteNewsIds.contains(it.id) })
        }
    }

    fun deleteAllFavourites() {
        GlobalScope.launch {
            database.newsDao().deleteAllFavorites()
        }
    }
}
