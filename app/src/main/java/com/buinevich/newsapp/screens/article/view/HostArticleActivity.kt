package com.buinevich.newsapp.screens.article.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import com.buinevich.newsapp.ARTICLE_TAG
import com.buinevich.newsapp.NEWS_ID
import com.buinevich.newsapp.R

class HostArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_article_activity)

        val newsId = intent.getStringExtra(NEWS_ID)!!

        supportFragmentManager.inTransaction {
            replace(R.id.article_fragment_container, ArticleFragment.newInstance(newsId), ARTICLE_TAG)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private inline fun FragmentManager.inTransaction(body: FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            body()
            commit()
        }
    }
}
