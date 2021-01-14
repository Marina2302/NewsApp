package com.buinevich.newsapp.screens.base.presenter

import com.buinevich.newsapp.screens.base.view.BaseView

interface BasePresenter<V : BaseView> {

    fun onAttach(view: V)

    fun onDetach()

    fun getView(): V?
}
