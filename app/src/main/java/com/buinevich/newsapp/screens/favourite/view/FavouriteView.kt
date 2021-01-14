package com.buinevich.newsapp.screens.favourite.view

import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.screens.base.view.BaseView

interface FavouriteView : BaseView {

    fun loadFavouritesNews()

    fun showNews(news: List<News>)

    fun deleteAllFavourites()

    fun initToolbar()

    fun initRecyclerView()

    fun setAdapterClickListener()

    fun openDetailScreen(id: String)

}
