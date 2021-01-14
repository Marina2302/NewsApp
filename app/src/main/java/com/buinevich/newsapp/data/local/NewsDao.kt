package com.buinevich.newsapp.data.local

import androidx.room.*
import com.buinevich.newsapp.data.local.entity.News
import io.reactivex.Single

@Dao
interface NewsDao {

    @Update
    fun updateFavouriteState(news: News)

    @Query("SELECT * FROM news")
    fun getNews(): Single<List<News>>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: String): Single<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(news: List<News>)

    @Query("SELECT * FROM news WHERE isFavourite = :isFavourite")
    fun getFavorites(isFavourite: Boolean): Single<List<News>>

    @Query("DELETE FROM news WHERE isFavourite = 1")
    fun deleteAllFavorites()

    @Query("SELECT * FROM news WHERE isFavourite = 1")
    fun getFavoritesNews(): List<News>

}
