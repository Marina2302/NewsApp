package com.buinevich.newsapp.data.remote.models

data class Article(
        val publishedAt: String?,
        val author: String?,
        val urlToImage: String?,
        val description: String?,
        val source: Source?,
        val title: String?,
        val url: String?
)
