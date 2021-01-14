package com.buinevich.newsapp.data.remote.models

data class NewsResponse(
		val totalResults: Int? = null,
		val articles: List<Article>? = null,
		val status: String? = null
)
