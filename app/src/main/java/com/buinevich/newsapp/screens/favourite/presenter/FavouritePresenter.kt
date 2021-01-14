package com.buinevich.newsapp.screens.favourite.presenter

import com.buinevich.newsapp.screens.base.presenter.BasePresenter
import com.buinevich.newsapp.screens.favourite.view.FavouriteView

interface FavouritePresenter<V : FavouriteView> : BasePresenter<V> {

    fun loadFavourites()

    fun deleteAllFavourites()

    fun openArticleScreen(id: String)

}
