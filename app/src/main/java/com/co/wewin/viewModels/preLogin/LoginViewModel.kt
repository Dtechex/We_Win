package com.co.wewin.viewModels.preLogin

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.LoginRequest
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.LoginResponse
import com.co.wewin.model.responses.RegisterResponse
import com.co.wewin.utilities.ToastUtils
import com.co.wewin.utilities.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.security.auth.callback.Callback

class LoginViewModel(private val dataSource: ApiDataSource):ViewModel() {
    private val _apilogin = MutableLiveData<BaseResponse<LoginResponse?>?>()
    val apilogin=_apilogin

    fun callLoginApi(loginRequest: LoginRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.loginDataSource(loginRequest, loginResponseHandler())
        }
    }

    private fun loginResponseHandler(): NetworkResponseListener<BaseResponse<LoginResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<LoginResponse?>?>{
            override fun onResponseSuccess(response: BaseResponse<LoginResponse?>?) {
                _apilogin.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<LoginResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<LoginResponse?>()
                    baseResponse.message=message
                    baseResponse.status=NetworkConstants.Status.FAILED
                    _apilogin.value=baseResponse
                }else{
                    _apilogin.value=response
                }

            }

        }
    }
}