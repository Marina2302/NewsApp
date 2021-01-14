package com.buinevich.newsapp.screens.article.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import com.squareup.picasso.Picasso
import com.buinevich.newsapp.*
import com.buinevich.newsapp.NewsApplication.Companion.appComponent
import com.buinevich.newsapp.data.local.entity.News
import com.buinevich.newsapp.screens.base.view.BaseFragment
import com.buinevich.newsapp.screens.article.presenter.ArticlePresenterImpl
import kotlinx.android.synthetic.main.fragment_article_description.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ArticleFragment : BaseFragment(), ArticleView {

    @Inject
    lateinit var presenter: ArticlePresenterImpl<ArticleView>

    companion object {
        fun newInstance(newsId: String) =
                ArticleFragment().apply {
                    arguments = Bundle(1).apply {
                        putString(NEWS_ID, newsId)
                    }
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initBtnFavourites()
        }
        initBtnWeb()
        presenter.onAttach(this)
        val newsId: String = arguments?.getString(NEWS_ID) ?: ""
        getArticleDetail(newsId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu_news_description, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareNews -> shareNews()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getArticleDetail(id: String) {
        presenter.loadNewsById(id)
    }

    override fun showArticleDetail(news: News) {
        tvTitle.text = news.title
        tvBody.text = news.description
        tvAuthor.text = news.author
        tvDate.text = news.publishedAt
        tvDate.text = news.publishedAt.let {
            DateUtils.formatDateTime(requireContext(),
                    SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault()).parse(it).time,
                    DateUtils.FORMAT_SHOW_DATE + DateUtils.FORMAT_SHOW_TIME)
        }
        if (news.urlToImage.isNotEmpty()) {
            Picasso.get().load(news.urlToImage).into(ivDescription)
        }
        when (news.isFavourite) {
            true -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                favourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star, activity?.theme))
            }
            false -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                favourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border, activity?.theme))
            }
        }
    }

    override fun showFullNews(url: String) {
        CustomTabsIntent.Builder().build().launchUrl(requireContext(), Uri.parse(url))
    }

    override fun changeFavourites(id: String) {
        presenter.changeFavourites(id)
    }

    override fun shareNews() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = TYPE_TEXT
        shareIntent.putExtra(Intent.EXTRA_TEXT, presenter.news.url)
        startActivity(Intent.createChooser(shareIntent, SHARE_TEXT))
    }

    override fun initToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbarNewsDescription as Toolbar)
            title = "Article"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun initBtnFavourites() {
        favourite.setOnClickListener {
            changeFavourites(arguments?.getString(NEWS_ID)!!)

            if (presenter.news.isFavourite) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    favourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border, activity?.theme))
                }
                presenter.news.isFavourite = false
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    favourite.setImageDrawable(resources.getDrawable(R.drawable.ic_star, activity?.theme))
                }
                presenter.news.isFavourite = true
            }
        }
    }

    override fun initBtnWeb() {
        btnWeb.setOnClickListener {
            presenter.showFullNews(arguments?.getString(NEWS_ID)!!)
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

}
