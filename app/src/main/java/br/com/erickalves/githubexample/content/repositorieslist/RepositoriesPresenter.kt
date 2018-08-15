package br.com.erickalves.githubexample.content.repositorieslist

import android.content.Intent
import android.net.Uri
import br.com.erickalves.githubexample.api.ApiManager
import br.com.erickalves.githubexample.api.ErrorHandler
import br.com.erickalves.githubexample.structure.BasePresenterImplementation
import rx.functions.Action1

/**
 * Created by erickalves on 06/07/2018.
 */
class RepositoriesPresenter : BasePresenterImplementation<RepositoriesContract.View>(), RepositoriesContract.Presenter {

    override fun loadRepositories(page: Int) {

        ApiManager.loadRepositories(page)
                .doOnError { mView?.showMessage(it.toString()) }
                .subscribe(Action1 { mView?.showRepositories(it) },
                        ErrorHandler(mView, { throwable, errorBody, isNetwork -> mView?.showError(throwable.message) }))
    }

    override fun openUrl(url: String){
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        mView?.getContext()?.startActivity(i)
    }
    override fun loadNextPageIfNeeded(totalItemCount:Int, lastVisibleItem:Int){
        //I set 3 here to the load more start 3 itens before we reach the end.
        val offsetItens = 3;

        if (totalItemCount <= lastVisibleItem + offsetItens) {

            mView?.getNextPage()?.let {
                loadRepositories(it)
            }

        }
    }




}