package com.buinevich.newsapp.screens.favourite.presenter

import com.buinevich.newsapp.data.NewsRepository
import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.router.Router
import com.buinevich.newsapp.screens.base.presenter.BasePresenterImpl
import com.buinevich.newsapp.screens.favourite.view.FavouriteView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavouritePresenterImpl<V : FavouriteView>
@Inject constructor(private val repository: NewsRepository, private val router: Router) : BasePresenterImpl<V>(), FavouritePresenter<V> {

    override fun deleteAllFavourites() {
        repository.deleteAllFavourites()
        getView()?.showNews(ArrayList<News>())
    }

    override fun openArticleScreen(id: String) {
        router.openArticleScreen(id)
    }

    override fun loadFavourites() {
        getView()?.showLoading()
        repository.getFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { news ->
                    getView()?.showNews(news)
                    getView()?.hideLoading()
                }
    }

}
