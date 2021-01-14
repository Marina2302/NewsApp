package com.buinevich.newsapp.screens.headlines.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.buinevich.newsapp.*
import com.buinevich.newsapp.NewsApplication.Companion.appComponent
import com.buinevich.newsapp.screens.base.view.BaseFragment
import com.buinevich.newsapp.screens.headlines.presenter.HeadLinesPresenterImpl
import kotlinx.android.synthetic.main.fragment_headlines.*
import javax.inject.Inject

class HeadLinesFragment : BaseFragment(), HeadLinesView, View.OnClickListener {

    @Inject
    lateinit var presenter: HeadLinesPresenterImpl<HeadLinesView>

    companion object {
        fun newInstance() = HeadLinesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_headlines, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_menu_news_headlines, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.checkHeadLines -> checkCategory()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        presenter.onAttach(this)
    }

    override fun checkCategory() {
        val radioButton =
                when (groupSort.checkedRadioButtonId) {
                    R.id.rbAll -> ALL_CATEGORIES
                    R.id.rbBusiness -> BUSINESS_CATEGORY
                    R.id.rbEntertainment -> ENTERTAINMENT_CATEGORY
                    R.id.rbGeneral -> GENERAL_CATEGORY
                    R.id.rbHealth -> HEALTH_CATEGORY
                    R.id.rbScience -> SCIENCE_CATEGORY
                    R.id.rbSports -> SPORTS_CATEGORY
                    R.id.rbTechnology -> TECHNOLOGY_CATEGORY
                    else -> ALL_CATEGORIES
                }
        presenter.openNewsScreen(radioButton, true)
    }

    override fun onClick(v: View?) {}

    override fun initToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbarHeadlines as Toolbar)
            title = "Top Headlines"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

}
