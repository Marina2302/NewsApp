package com.buinevich.newsapp.screens.news.presenter

import com.buinevich.newsapp.data.NewsRepository
import com.buinevich.newsapp.router.Router
import com.buinevich.newsapp.screens.base.presenter.BasePresenterImpl
import com.buinevich.newsapp.screens.news.view.NewsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsPresenterImpl<V : NewsView>
@Inject constructor(private val repository: NewsRepository, private val router: Router) : BasePresenterImpl<V>(), NewsPresenter<V> {

    override fun loadNews() {
        getView()?.showLoading()
        repository.getStandardNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ t ->
                    getView()?.apply {
                        showNews(t)
                        hideLoading()
                    }
                }), ({
                    getView()?.apply {
                        showSnackBar(true)
                        hideLoading()
                    }
                }))
    }

    override fun loadNewsByQuery(query: String) {
        getView()?.showLoading()
        repository.getNewsByQuery(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ t ->
                    getView()?.apply {
                        showNews(t)
                        repository.insertNews(t)
                        hideLoading()
                    }
                }), ({
                    getView()?.apply {
                        showSnackBar(true)
                        hideLoading()
                    }
                }))
    }

    override fun loadTopHeadlinesByCategory(category: String) {
        getView()?.showLoading()
        repository.getTopHeadlinesByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ t ->
                    getView()?.apply {
                        showNews(t)
                        repository.insertNews(t)
                        hideLoading()
                    }
                }), ({
                    getView()?.apply {
                        showSnackBar(true)
                        hideLoading()
                    }
                }))
    }

    override fun openArticleScreen(id: String) {
        router.openArticleScreen(id)
    }

    override fun openFavouritesScreen() {
        router.openFavouriteScreen()
    }

    override fun openHeadLinesScreen() {
        router.openHeadLinesScreen()
    }

}
