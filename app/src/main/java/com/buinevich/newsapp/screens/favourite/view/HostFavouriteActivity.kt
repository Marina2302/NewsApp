package com.buinevich.newsapp.screens.favourite.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import com.buinevich.newsapp.FAVOURITE_TAG
import com.buinevich.newsapp.R

class HostFavouriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_favourite_activity)

        supportFragmentManager.inTransaction {
            replace(R.id.favourite_fragment_container, FavouriteFragment.newInstance(), FAVOURITE_TAG)
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
