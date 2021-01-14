package com.buinevich.newsapp.di

import android.content.Context
import com.buinevich.newsapp.screens.article.view.ArticleFragment
import com.buinevich.newsapp.screens.favourite.view.FavouriteFragment
import com.buinevich.newsapp.screens.headlines.view.HeadLinesFragment
import com.buinevich.newsapp.screens.news.view.NewsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(newsFragment: NewsFragment)

    fun inject(articleFragment: ArticleFragment)

    fun inject(filterFragment: HeadLinesFragment)

    fun inject(favouriteFragment: FavouriteFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(context: Context): Builder
    }
}
