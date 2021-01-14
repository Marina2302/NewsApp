package com.buinevich.newsapp.screens.base.presenter

import com.buinevich.newsapp.screens.base.view.BaseView

open class BasePresenterImpl<V : BaseView> : BasePresenter<V> {

    private var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun getView(): V? {
        return view
    }
}
