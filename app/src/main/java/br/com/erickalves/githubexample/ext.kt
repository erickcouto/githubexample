package br.com.erickalves.githubexample

import com.squareup.moshi.Moshi

/**
 * Created by erickalves on 06/07/2018.
 */

inline fun <reified T> Moshi.fromJson(json: String): T = this.adapter(T::class.java).fromJson(json)
