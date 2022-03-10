package com.co.wewin.utilities.network

import android.util.Log
import com.co.wewin.model.responses.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.co.wewin.model.responses.RegisterResponse

import org.json.JSONObject
import java.lang.Exception


class CommonResponseHandler<T> (private val mCallback: NetworkResponseListener<T>) :
    Callback<T> {
    private val TAG = "CommonResponseHandler"
    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (call.isCanceled){
            return
        }
        var baseResponse = response.body()
        if (response.isSuccessful && baseResponse != null) {
            if (baseResponse is BaseResponse<*>){
                if (baseResponse.status) {
                    mCallback.onResponseSuccess(baseResponse)
                } else {
                    mCallback.onResponseFailure(baseResponse.message, baseResponse, null)
                }
            }

        }else{

            var message=""
            try {
                val jObjError = JSONObject(response.errorBody()!!.string())
                message=jObjError.getString("message")
            }catch (e: Exception) {
                Log.e(TAG, "onResponse: "+e.message )
            }


            if (message==""){
                message= response.message()
            }

//            if (message.isNullOrEmpty()) {
//                message = checkForKnownErrorMessages(response.code())
//            }

            mCallback.onResponseFailure(message, baseResponse, response.code())
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(TAG, "onFailure: "+t.message )
    }

}