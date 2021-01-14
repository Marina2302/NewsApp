package com.buinevich.newsapp.screens.article.presenter

import com.buinevich.newsapp.screens.base.presenter.BasePresenter
import com.buinevich.newsapp.screens.article.view.ArticleView

interface ArticlePresenter<V : ArticleView> : BasePresenter<V> {

    fun loadNewsById(id: String)

    fun showFullNews(id: String)

    fun changeFavourites(id: String)

}
