package br.com.erickalves.githubexample.api

import android.support.annotation.StringRes
import android.text.TextUtils
import br.com.erickalves.githubexample.R
import br.com.erickalves.githubexample.fromJson
import br.com.erickalves.githubexample.structure.BaseView
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import rx.functions.Action1
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by erickalves on 06/07/2018.
 */
class ErrorHandler(view: BaseView? = null, val onFailure: (Throwable, ErrorBody?, Boolean) -> Unit) : Action1<Throwable> {

    private val mViewReference: WeakReference<BaseView>

    init {
        mViewReference = WeakReference<BaseView>(view)
    }

    override fun call(throwable: Throwable) {
        var isNetwork = false
        var errorBody: ErrorBody? = null
        if (isNetworkError(throwable)) {
            isNetwork = true
            showMessage(mViewReference.get()?.getContext()!!.getString(R.string.no_internet_error))
        } else if (throwable is HttpException) {
            errorBody = ErrorBody.parseError(throwable.response())
            if (errorBody != null) {
                handleError(errorBody)
            }
        }

        onFailure(throwable, errorBody, isNetwork)
    }

    private fun isNetworkError(throwable: Throwable): Boolean {
        return throwable is SocketException ||
                throwable is UnknownHostException ||
                throwable is SocketTimeoutException
    }

    private fun handleError(errorBody: ErrorBody) {
        if (errorBody.code != ErrorBody.UNKNOWN_ERROR) {
            mViewReference.get()?.showError(mViewReference.get()?.getContext()!!.getString(R.string.unknown_error))
        }
    }

    private fun showMessage(error: String) {
        if (error.isNotEmpty()) {
            mViewReference.get()?.showError(error)
        }
    }

}

data class ErrorBody(val code: Int, @Json(name = "error") private val message: String) {

    override fun toString(): String = message

    companion object {

        val UNKNOWN_ERROR = 0

        private val moshi = Moshi.Builder().build()

        fun parseError(response: Response<*>?): ErrorBody? {
            return (response?.errorBody())?.let {
                try {
                    moshi.fromJson(it.string())
                } catch (ignored: IOException) {
                    null
                }
            }
        }
    }

}