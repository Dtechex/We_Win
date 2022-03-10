package com.co.wewin.viewModels.preLogin

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.RegisterRequest
import com.co.wewin.model.requests.SendOtpRequest
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.RegisterResponse
import com.co.wewin.model.responses.SendOtpResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterViewModel(val dataSource: ApiDataSource):ViewModel() {

    private val _sendOtpApiResult=MutableLiveData<BaseResponse<SendOtpResponse?>?>()
    var sendOtpApiResult=_sendOtpApiResult

    private val _registerApiResult=MutableLiveData<BaseResponse<RegisterResponse?>?>()
    var registerApiResult=_registerApiResult

    fun callSendOtpApi(sendOtpRequest: SendOtpRequest?){
        GlobalScope.launch(Dispatchers.Main){
            dataSource.sendOtpDataSource(sendOtpRequest,otpResponseHandler())
        }
    }

    private fun otpResponseHandler(): NetworkResponseListener<BaseResponse<SendOtpResponse?>?> {
        return object:NetworkResponseListener<BaseResponse<SendOtpResponse?>?>{
            override fun onResponseSuccess(
                response: BaseResponse<SendOtpResponse?>?
            ) {
                _sendOtpApiResult.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<SendOtpResponse?>?,
                code: Int?
            ) {
                _sendOtpApiResult.value=response!!
            }

        }

    }

    fun callRegisterApi(registerRequest: RegisterRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.registerDataSource(registerRequest,registerResponseHandler())
        }
    }

    private fun registerResponseHandler(): NetworkResponseListener<BaseResponse<RegisterResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<RegisterResponse?>?>{
            override fun onResponseSuccess(response: BaseResponse<RegisterResponse?>?) {
                _registerApiResult.value=response!!
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<RegisterResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    var baseResponse=BaseResponse<RegisterResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _registerApiResult.value=baseResponse
                }else{
                    _registerApiResult.value=response!!
                }

            }

        }
    }


}