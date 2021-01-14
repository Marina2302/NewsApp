package com.buinevich.newsapp

import android.app.Application
import com.buinevich.newsapp.di.AppComponent
import com.buinevich.newsapp.di.DaggerAppComponent

class NewsApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
    }

}
