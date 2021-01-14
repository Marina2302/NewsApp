package com.buinevich.newsapp.screens.news.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.buinevich.newsapp.CATEGORY
import com.buinevich.newsapp.NewsApplication.Companion.appComponent
import com.buinevich.newsapp.R
import com.buinevich.newsapp.SORT_BY
import com.buinevich.newsapp.TOP_HEADLINES
import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.screens.NewsAdapter
import com.buinevich.newsapp.screens.base.view.BaseFragment
import com.buinevich.newsapp.screens.news.presenter.NewsPresenterImpl
import kotlinx.android.synthetic.main.fragment_news_list.*
import javax.inject.Inject

class NewsFragment : BaseFragment(), NewsView {

    @Inject
    lateinit var presenter: NewsPresenterImpl<NewsView>

    @Inject
    lateinit var newsAdapter: NewsAdapter

    companion object {
        fun newInstance(param: String = "", topHeadlines: Boolean = false) =
                NewsFragment().apply {
                    arguments = Bundle(3).apply {
                        if (topHeadlines) {
                            putString(CATEGORY, param)
                            putBoolean(TOP_HEADLINES, true)
                        } else {
                            putString(SORT_BY, param)
                        }
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
        initBtnFavourites()
        setAdapterClickListener()
        presenter.onAttach(this)
        if (arguments?.getBoolean(TOP_HEADLINES) == true) {
            val category: String = arguments?.getString(CATEGORY) ?: ""
            searchTopHeadlinesByCategory(category)
        } else {
            loadNews()
        }
    }

    override fun loadNews() {
        presenter.loadNews()
    }

    override fun searchNewsByQuery(query: String) {
        presenter.loadNewsByQuery(query)
    }

    override fun searchTopHeadlinesByCategory(category: String) {
        presenter.loadTopHeadlinesByCategory(category)
    }

    override fun showNews(news: List<News>) {
        newsAdapter.updateAdapter(news)
    }

    override fun openArticleScreen(id: String) {
        presenter.openArticleScreen(id)
    }

    override fun openFavouritesScreen() {
        presenter.openFavouritesScreen()
    }

    override fun openHeadLinesScreen() {
        presenter.openHeadLinesScreen()
    }

    override fun initToolbar() {
        this.setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbarNewsList as Toolbar)
            title = "News"
        }
    }

    override fun initRecyclerView() {
        (newsList as RecyclerView).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = newsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        favourite.visibility = View.GONE
                    } else {
                        favourite.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    override fun initBtnFavourites() {
        favourite.setOnClickListener {
            openFavouritesScreen()
        }
    }

    override fun showSnackBar(isSearchFailed: Boolean) {
        if (isSearchFailed) {
            Snackbar.make(favourite, resources.getString(R.string.snackbar_error_connection), Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    override fun setAdapterClickListener() {
        newsAdapter.setClickListener(object : NewsAdapter.ClickListener {
            override fun onClick(news: News, position: Int) {
                openArticleScreen(news.id)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu_news_list, menu)

        val menuItem = menu.findItem(R.id.findNews)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchNewsByQuery(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isEmpty()) {
                    loadNews()
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.topHeadlines -> presenter.openHeadLinesScreen()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

}
