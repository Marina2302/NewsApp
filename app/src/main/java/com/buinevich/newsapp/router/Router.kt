package com.buinevich.newsapp.router

interface Router {

    fun openNewsScreen(category: String, topHeadlines: Boolean)

    fun openArticleScreen(id: String)

    fun openFavouriteScreen()

    fun openHeadLinesScreen()
}
