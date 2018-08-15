package br.com.erickalves.githubexample.structure

/**
 * Created by erickalves on 06/07/2018.
 */
interface BasePresenter<in V : BaseView> {

    fun attachView(view: V)

    fun detachView()
}