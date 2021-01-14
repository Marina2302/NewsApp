package com.buinevich.newsapp.screens.article.view

import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.screens.base.view.BaseView

interface ArticleView : BaseView {

    fun getArticleDetail(id: String)

    fun changeFavourites(id: String)

    fun showArticleDetail(news: News)

    fun showFullNews(url: String)

    fun shareNews()

    fun initToolbar()

    fun initBtnFavourites()

    fun initBtnWeb()

}
