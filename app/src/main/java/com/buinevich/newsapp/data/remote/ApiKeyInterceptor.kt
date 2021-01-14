package com.buinevich.newsapp.data.remote

import com.buinevich.newsapp.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url().newBuilder()
                .addQueryParameter("apiKey", API_KEY)
                .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
