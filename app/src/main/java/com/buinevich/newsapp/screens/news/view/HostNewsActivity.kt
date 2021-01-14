package com.buinevich.newsapp.screens.news.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.buinevich.newsapp.*

class HostNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_news_activity)

        when (intent.extras) {
            null -> {
                supportFragmentManager.inTransaction {
                    replace(R.id.news_fragment_container, NewsFragment.newInstance(), NEWS_TAG)
                }
            }
            else -> {
                val category = intent.getStringExtra(CATEGORY)!!
                supportFragmentManager.inTransaction {
                    replace(R.id.news_fragment_container, NewsFragment.newInstance(category, true), NEWS_TAG)
                }
            }
        }
    }

    private inline fun FragmentManager.inTransaction(body: FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            body()
            commit()
        }
    }

}
