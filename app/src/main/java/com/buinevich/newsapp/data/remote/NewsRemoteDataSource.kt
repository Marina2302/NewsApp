package com.buinevich.newsapp.data.remote

import com.buinevich.newsapp.DATE_FORMAT_SERVER
import com.buinevich.newsapp.SORT_BY_PUBLISHED_AT
import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.data.remote.models.Article
import com.buinevich.newsapp.data.remote.models.NewsResponse
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewsRemoteDataSource(private val apiService: NewsService) : RemoteDataSource {

    override fun getStandardNews(): Single<List<News>> {
        val formatterDayTime = SimpleDateFormat(DATE_FORMAT_SERVER)
        val from = Calendar.getInstance()
        from.add(Calendar.DATE, -1)
        val to = Calendar.getInstance()
        return apiService.requestNewsByQuery("all", formatterDayTime.format(from.time), formatterDayTime.format(to.time), Locale.getDefault().language, SORT_BY_PUBLISHED_AT).onErrorReturn { NewsResponse() }.map { it.articles?.convertToNews() }
    }

    override fun getNewsByQuery(query: String): Single<List<News>> {
        return apiService.requestNewsByQuery(query, "", "", Locale.getDefault().language, SORT_BY_PUBLISHED_AT).onErrorReturn { NewsResponse() }.map { it.articles?.convertToNews() }
    }

    override fun getTopHeadlinesByCategory(category: String): Single<List<News>> {
        return apiService.requestTopHeadlinesByCategory(category, Locale.getDefault().country).onErrorReturn { NewsResponse() }.map { it.articles?.convertToNews() }
    }

    private fun List<Article>.convertToNews(): List<News> {
        val list = ArrayList<News>()
        this.forEach {
            list.add(News(
                    "" + SimpleDateFormat(DATE_FORMAT_SERVER).parse(it.publishedAt)?.time + it.title,
                    it.publishedAt ?: "",
                    it.author ?: "Unknown author",
                    it.urlToImage ?: "",
                    it.description ?: "",
                    it.title ?: "",
                    it.url ?: "",
                    isFavourite = false))
        }
        return list
    }
}
