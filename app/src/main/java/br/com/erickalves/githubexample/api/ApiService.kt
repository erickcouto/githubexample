package br.com.erickalves.githubexample.api

import br.com.erickalves.githubexample.model.Repository
import br.com.erickalves.githubexample.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by erickalves on 06/07/2018.
 */

interface ApiService {

    @GET("search/repositories?q=android")
    fun getRepositories(@Query("page") page: Int,@Query("per_page") per_page: Int): Observable<SearchResult>

}