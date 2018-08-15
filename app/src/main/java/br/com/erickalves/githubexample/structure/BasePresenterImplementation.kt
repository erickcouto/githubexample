package br.com.erickalves.githubexample.structure

/**
 * Created by erickalves on 06/07/2018.
 */
open class BasePresenterImplementation<V : BaseView> : BasePresenter<V> {

    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}