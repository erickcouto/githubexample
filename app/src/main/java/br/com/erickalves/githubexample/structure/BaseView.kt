package br.com.erickalves.githubexample.structure

import android.content.Context
/**
 * Created by erickalves on 06/07/2018.
 */
interface BaseView{
    fun getContext(): Context

    fun showError(error: String?)

    fun showMessage(message: String)
}