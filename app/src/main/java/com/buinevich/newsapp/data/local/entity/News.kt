package com.buinevich.newsapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(@PrimaryKey @NonNull val id: String,
                val publishedAt: String,
                val author: String,
                val urlToImage: String,
                val description: String,
                val title: String,
                val url: String,
                var isFavourite: Boolean
)
