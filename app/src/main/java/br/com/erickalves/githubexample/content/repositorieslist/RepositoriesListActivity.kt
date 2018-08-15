package br.com.erickalves.githubexample.content.repositorieslist

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.erickalves.githubexample.R
import br.com.erickalves.githubexample.api.ApiManager
import br.com.erickalves.githubexample.model.Repository
import br.com.erickalves.githubexample.model.SearchResult
import br.com.erickalves.githubexample.structure.BaseActivity
import com.malinskiy.superrecyclerview.OnMoreListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


/**
 * Created by erickalves on 06/07/2018.
 */
class RepositoriesListActivity : BaseActivity<RepositoriesContract.View,
        RepositoriesPresenter>(),
        RepositoriesContract.View, OnMoreListener {


    private var mAdapter: RepositoriesAdapter? = null
    override var mPresenter: RepositoriesPresenter = RepositoriesPresenter()
    lateinit var linearLayoutManager: LinearLayoutManager
    var page: Int = 1;

    override fun getNextPage(): Int {
        return page++
    }

    override fun showRepositories(searchResult: SearchResult) {

        //if we reach the end of pagination we remove the MoreListenerhere.
        if (searchResult.items.size < ApiManager.PER_PAGE) {
            repositories_rv.removeMoreListener()
        }
        mAdapter?.addRepositories(searchResult.items)
        mAdapter?.notifyDataSetChanged()
        hideProgress()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()
        mPresenter.loadRepositories(page)

        setTitle(getString(R.string.screen_title_repositories))
        showProgress()


    }

    private fun setUpRecyclerView() {

        mAdapter = RepositoriesAdapter(ArrayList<Repository>(), {
            showAccessOptionDialog(it)
        })
        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        repositories_rv.setLayoutManager(linearLayoutManager)
        repositories_rv.adapter = mAdapter


        //adding the OnScrollListener for the Load more Implementation
        repositories_rv.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        repositories_rv.setupMoreListener(this, 1)


    }

    //Load more Implementation
    //I used this library to make the Load More implementation easelly and more readable
    override fun onMoreAsked(overallItemsCount: Int, itemsBeforeMore: Int, maxLastVisiblePosition: Int) {

        var totalItemCount = linearLayoutManager.getItemCount();
        var lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

        mPresenter.loadNextPageIfNeeded(totalItemCount, lastVisibleItem)
    }


    private fun showAccessOptionDialog(repository: Repository): Boolean {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_title))
        builder.setMessage(getString(R.string.choose_description))
        builder.setPositiveButton(getString(R.string.repository_choose), DialogInterface.OnClickListener { arg0, arg1 ->
            if (repository.html_url != null) {
                mPresenter.openUrl(repository.html_url)
            }
        })
        builder.setNegativeButton(getString(R.string.owner_choose), DialogInterface.OnClickListener { arg0, arg1 ->
            if (repository.owner != null && repository.owner.html_url != null) {
                mPresenter.openUrl(repository.owner.html_url)
            }
        })

        builder.create().show()
        return true
    }

    private fun showProgress() {
        repositories_rv.visibility = View.GONE
        progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        repositories_rv.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
    }

    override fun showError(error: String?) {
        super.showError(error)
        progress_bar.visibility = View.GONE
    }

}
