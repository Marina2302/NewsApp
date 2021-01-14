package com.buinevich.newsapp.screens.favourite.view

import android.os.Bundle

import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.buinevich.newsapp.NewsApplication.Companion.appComponent
import com.buinevich.newsapp.R
import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.screens.NewsAdapter
import com.buinevich.newsapp.screens.base.view.BaseFragment
import com.buinevich.newsapp.screens.favourite.presenter.FavouritePresenterImpl
import kotlinx.android.synthetic.main.fragment_favourites_news_list.*
import javax.inject.Inject

class FavouriteFragment : BaseFragment(), FavouriteView {

    @Inject
    lateinit var presenter: FavouritePresenterImpl<FavouriteView>

    @Inject
    lateinit var newsAdapter: NewsAdapter

    companion object {
        fun newInstance() = FavouriteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourites_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        setAdapterClickListener()
        presenter.onAttach(this)
    }

    override fun onResume() {
        super.onResume()
        loadFavouritesNews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu_favourites_news_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteNews -> deleteAllFavourites()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun loadFavouritesNews() {
        presenter.loadFavourites()
    }

    override fun showNews(news: List<News>) {
        newsAdapter.updateAdapter(news)
    }

    override fun deleteAllFavourites() {
        presenter.deleteAllFavourites()
        (favouritesList as RecyclerView).apply {
            adapter?.notifyDataSetChanged()
        }
    }

    override fun openDetailScreen(id: String) {
        presenter.openArticleScreen(id)
    }

    override fun initToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbarFavourites as Toolbar)
            title = "Favourites news"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun initRecyclerView() {
        (favouritesList as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
        }
    }

    override fun setAdapterClickListener() {
        newsAdapter.setClickListener(object : NewsAdapter.ClickListener {
            override fun onClick(news: News, position: Int) {
                openDetailScreen(news.id)
            }
        })
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

}
