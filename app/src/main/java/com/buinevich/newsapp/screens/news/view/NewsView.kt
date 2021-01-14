package com.buinevich.newsapp.screens.news.view

import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.screens.base.view.BaseView

interface NewsView : BaseView {

    fun loadNews()

    fun searchNewsByQuery(query: String)

    fun searchTopHeadlinesByCategory(category: String)

    fun showNews(news: List<News>)

    fun openArticleScreen(id: String)

    fun openFavouritesScreen()

    fun openHeadLinesScreen()

    fun setAdapterClickListener()

    fun initToolbar()

    fun initRecyclerView()

    fun initBtnFavourites()

    fun showSnackBar(isSearchFailed: Boolean)

}
