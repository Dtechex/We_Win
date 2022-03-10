package com.co.wewin.model.responses

import com.co.wewin.utilities.network.NetworkConstants
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("status")
    var status: Boolean = NetworkConstants.Status.SUCCESS

    @SerializedName("message")
    var message: String? = null

    @SerializedName("request_id")
    var request_id: String? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("data")
    val data: T? = null

    @SerializedName("code")
    val code: String? = null

    @SerializedName("statusCode")
    val statusCode: Int? = null

    @SerializedName("name")
    val name: Int? = null

    @SerializedName("error")
    val error: String? = null


}