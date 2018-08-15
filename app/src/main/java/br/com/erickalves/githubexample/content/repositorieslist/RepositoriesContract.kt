package br.com.erickalves.githubexample.content.repositorieslist

import br.com.erickalves.githubexample.model.SearchResult
import br.com.erickalves.githubexample.structure.BasePresenter
import br.com.erickalves.githubexample.structure.BaseView

/**
 * Created by erickalves on 06/07/2018.
 */
object RepositoriesContract {

    interface View : BaseView {
        fun showRepositories(searchResult: SearchResult)
        fun getNextPage():Int;

    }

    interface Presenter : BasePresenter<View> {
        fun loadRepositories(page:Int)
        fun openUrl(url:String)
        fun loadNextPageIfNeeded(totalItemCount:Int, lastVisibleItem:Int)

    }


}
