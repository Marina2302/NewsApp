package com.buinevich.newsapp.screens.headlines.presenter

import com.buinevich.newsapp.screens.base.presenter.BasePresenter
import com.buinevich.newsapp.screens.headlines.view.HeadLinesView

interface HeadLinesPresenter<V : HeadLinesView> : BasePresenter<V> {

    fun openNewsScreen(category: String, topHeadlines: Boolean)

}
