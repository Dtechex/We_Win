package com.co.wewin.utilities.network

import android.util.ArrayMap

interface NetworkResponseListener<T> {

    fun onResponseSuccess(response: T)

    fun onResponseFailure(message: String?, response: T?, code: Int?)

}