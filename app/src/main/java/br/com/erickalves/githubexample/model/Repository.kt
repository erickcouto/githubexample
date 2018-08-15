package br.com.erickalves.githubexample.model

/**
 * Created by erickalves on 06/07/2018.
 */
data class Repository(val id: Long, val name: String?, val description: String?, val fork: Boolean?, val html_url: String?, val owner:Owner?)