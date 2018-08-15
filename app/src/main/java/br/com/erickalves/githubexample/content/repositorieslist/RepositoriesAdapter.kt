package br.com.erickalves.githubexample.content.repositorieslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.erickalves.githubexample.R
import br.com.erickalves.githubexample.model.Repository
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.repository_item.*
import com.bumptech.glide.request.RequestOptions

/**
 * Created by erickalves on 06/07/2018.
 */
class RepositoriesAdapter(private val repositories: MutableList<Repository>,
                          private val onLongClick: (Repository) -> Boolean)
    : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.repository_item, parent, false).let {
                    ViewHolder(it, onLongClick)
                }
    }

    class ViewHolder(override val containerView: View, private val onLongClick: (Repository) -> Boolean) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindData(repository: Repository) {
            with(repository) {
                if (name != null) {
                    repository_name_tv.text = name
                }
                if (description != null) {
                    description_tv.text = description
                }
                if (owner != null) {
                    owner_login_tv.text = owner.login
                }

                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.ic_launcher_background)
                if (owner?.avatar_url != null) {
                    Glide.with(containerView).setDefaultRequestOptions(requestOptions).load(owner.avatar_url).into(owner_avatar_iv);
                }

                containerView.setOnLongClickListener { onLongClick(repository) }
            }
        }
    }

    fun addRepositories(newRepositories: List<Repository>) {
        repositories.addAll(newRepositories)
    }
}