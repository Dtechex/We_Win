package com.co.wewin.utilities.network

open class BaseDataSource {

    fun url(api: String): String {
        val BASE_URL = NetworkConstants.BASE_URL;
        return "$BASE_URL$api"
    }
}