package com.buinevich.newsapp.screens.news.presenter

import com.buinevich.newsapp.screens.base.presenter.BasePresenter
import com.buinevich.newsapp.screens.news.view.NewsView

interface NewsPresenter<V : NewsView> : BasePresenter<V> {

    fun loadNews()

    fun loadNewsByQuery(query: String)

    fun loadTopHeadlinesByCategory(category: String)

    fun openArticleScreen(id: String)

    fun openFavouritesScreen()

    fun openHeadLinesScreen()

}
