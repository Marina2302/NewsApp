package com.buinevich.newsapp.screens.headlines.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import com.buinevich.newsapp.HEADLINE_TAG
import com.buinevich.newsapp.R

class HostHeadLinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_headlines_activity)

        supportFragmentManager.inTransaction {
            replace(R.id.headlines_fragment_container, HeadLinesFragment.newInstance(), HEADLINE_TAG)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private inline fun FragmentManager.inTransaction(body: FragmentTransaction.() -> Unit) {
        beginTransaction().apply {
            body()
            commit()
        }
    }

}
