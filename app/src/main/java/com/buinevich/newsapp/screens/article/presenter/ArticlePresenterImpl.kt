package com.buinevich.newsapp.screens.article.presenter

import android.util.Log
import com.buinevich.newsapp.data.NewsRepository
import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.screens.base.presenter.BasePresenterImpl
import com.buinevich.newsapp.screens.article.view.ArticleView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlePresenterImpl<V : ArticleView>
@Inject constructor(private val repository: NewsRepository) : BasePresenterImpl<V>(), ArticlePresenter<V> {
    lateinit var news: News
    override fun loadNewsById(id: String) {
        repository.getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ news ->
                    getView()?.showArticleDetail(news)
                    this.news = news
                }, {
                    Log.i("ArticlePresenterImpl", "error in loadNewsById(id: Long) method: ${it.message}")
                })
    }

    override fun showFullNews(id: String){
        repository.getNewsById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ news ->
                    getView()?.showFullNews(news.url)
                }, {
                    Log.i("ArticlePresenterImpl", "error in showFullNews(id: Long) method: ${it.message}")
                })
    }

    override fun changeFavourites(id: String) {
        repository.changeFavouriteState(id)
    }

}
