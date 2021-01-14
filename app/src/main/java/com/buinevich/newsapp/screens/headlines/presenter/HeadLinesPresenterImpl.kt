package com.buinevich.newsapp.screens.headlines.presenter

import com.buinevich.newsapp.router.Router
import com.buinevich.newsapp.screens.base.presenter.BasePresenterImpl
import com.buinevich.newsapp.screens.headlines.view.HeadLinesView
import javax.inject.Inject

class HeadLinesPresenterImpl<V : HeadLinesView>
@Inject constructor(private val router: Router) : BasePresenterImpl<V>(), HeadLinesPresenter<V> {

    override fun openNewsScreen(category: String, topHeadlines: Boolean) {
        router.openNewsScreen(category, topHeadlines)
    }

}
