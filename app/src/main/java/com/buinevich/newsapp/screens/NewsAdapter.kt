package com.buinevich.newsapp.screens

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import com.buinevich.newsapp.DATE_FORMAT_SERVER
import com.buinevich.newsapp.R
import com.buinevich.newsapp.data.local.entity.News
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_list_item.*
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var news: List<News> = emptyList()
    private lateinit var clickListener: ClickListener
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    interface ClickListener {
        fun onClick(news: News, position: Int)
    }

    fun updateAdapter(news: List<News>) {
        this.news = news
        notifyDataSetChanged()
    }

    fun setClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = inflater.inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindNews(news[position])
    }

    override fun getItemCount(): Int = if (news.isEmpty()) 0 else news.size

    inner class NewsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindNews(news: News) {
            tvTitleNews.text = news.title
            tvAuthorNews.text = news.author
            when (news.isFavourite) {
                true -> ibFavourite.visibility = View.VISIBLE
                false -> ibFavourite.visibility = View.GONE
            }
            tvDateNews.text = news.publishedAt.let {
                DateUtils.formatDateTime(containerView.context,
                        SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault()).parse(it).time,
                        DateUtils.FORMAT_SHOW_DATE + DateUtils.FORMAT_SHOW_TIME)
            }
            if (news.urlToImage.isNotEmpty()) {
                Picasso.get().load(news.urlToImage).into(ivNewsList)
            }
        }

        init {
            itemView.setOnClickListener {
                clickListener.onClick(news[adapterPosition], adapterPosition)
            }
        }
    }

}
