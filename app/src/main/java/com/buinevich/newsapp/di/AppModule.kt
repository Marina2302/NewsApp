package com.buinevich.newsapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.buinevich.newsapp.DATABASE_NAME
import com.buinevich.newsapp.URL_API
import com.buinevich.newsapp.data.NewsRepository
import com.buinevich.newsapp.data.local.NewsDatabase
import com.buinevich.newsapp.data.local.NewsLocalDataSource
import com.buinevich.newsapp.data.remote.ApiKeyInterceptor
import com.buinevich.newsapp.data.remote.NewsRemoteDataSource
import com.buinevich.newsapp.data.remote.NewsService
import com.buinevich.newsapp.router.Router
import com.buinevich.newsapp.router.RouterImpl
import com.buinevich.newsapp.screens.NewsAdapter
import com.buinevich.newsapp.screens.article.presenter.ArticlePresenter
import com.buinevich.newsapp.screens.article.presenter.ArticlePresenterImpl
import com.buinevich.newsapp.screens.article.view.ArticleView
import com.buinevich.newsapp.screens.favourite.presenter.FavouritePresenter
import com.buinevich.newsapp.screens.favourite.presenter.FavouritePresenterImpl
import com.buinevich.newsapp.screens.favourite.view.FavouriteView
import com.buinevich.newsapp.screens.headlines.presenter.HeadLinesPresenter
import com.buinevich.newsapp.screens.headlines.presenter.HeadLinesPresenterImpl
import com.buinevich.newsapp.screens.headlines.view.HeadLinesView
import com.buinevich.newsapp.screens.news.presenter.NewsPresenter
import com.buinevich.newsapp.screens.news.presenter.NewsPresenterImpl
import com.buinevich.newsapp.screens.news.view.NewsView
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNewsPresenter(repository: NewsRepository, router: Router): NewsPresenter<*> = NewsPresenterImpl<NewsView>(repository, router)

    @Singleton
    @Provides
    fun provideArticlePresenter(repository: NewsRepository): ArticlePresenter<*> = ArticlePresenterImpl<ArticleView>(repository)

    @Singleton
    @Provides
    fun provideFavouritePresenter(repository: NewsRepository, router: Router): FavouritePresenter<*> = FavouritePresenterImpl<FavouriteView>(repository, router)

    @Singleton
    @Provides
    fun provideHeadLinesPresenter(router: Router): HeadLinesPresenter<*> = HeadLinesPresenterImpl<HeadLinesView>(router)

    @Singleton
    @Provides
    fun provideNewsRepository(localSource: NewsLocalDataSource, remoteSource: NewsRemoteDataSource): NewsRepository = NewsRepository(localSource, remoteSource)

    @Singleton
    @Provides
    fun provideNewsLocalDataSource(database: NewsDatabase): NewsLocalDataSource = NewsLocalDataSource(database)

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(apiService: NewsService): NewsRemoteDataSource = NewsRemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideNewsDatabase(application: Context): NewsDatabase = Room.databaseBuilder(application, NewsDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideRouter(context: Context): Router = RouterImpl(context)

    @Provides
    fun provideNewsAdapter(context: Context): NewsAdapter = NewsAdapter(context)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .client(OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).build())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(URL_API).build()
    }

    @Singleton
    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService = retrofit.create(NewsService::class.java)
}
