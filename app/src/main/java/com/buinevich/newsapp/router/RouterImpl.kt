package com.buinevich.newsapp.router

import android.content.Context
import android.content.Intent
import com.buinevich.newsapp.CATEGORY
import com.buinevich.newsapp.NEWS_ID
import com.buinevich.newsapp.TOP_HEADLINES
import com.buinevich.newsapp.screens.article.view.HostArticleActivity
import com.buinevich.newsapp.screens.favourite.view.HostFavouriteActivity
import com.buinevich.newsapp.screens.headlines.view.HostHeadLinesActivity
import com.buinevich.newsapp.screens.news.view.HostNewsActivity

class RouterImpl(private val context: Context) : Router {

    override fun openNewsScreen(category: String, topHeadlines: Boolean) {
        context.startActivity(Intent(context, HostNewsActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(CATEGORY, category)
            putExtra(TOP_HEADLINES, topHeadlines)
        })
    }

    override fun openArticleScreen(id: String) {
        context.startActivity(Intent(context, HostArticleActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(NEWS_ID, id))
    }

    override fun openFavouriteScreen() {
        context.startActivity(Intent(context, HostFavouriteActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun openHeadLinesScreen() {
        context.startActivity(Intent(context, HostHeadLinesActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}
