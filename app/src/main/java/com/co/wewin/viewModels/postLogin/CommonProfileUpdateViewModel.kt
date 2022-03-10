package com.co.wewin.viewModels.postLogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.ChangeNickNameRequest
import com.co.wewin.model.requests.ChangePasswordRequest
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.UpdateProfileCommonResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class CommonProfileUpdateViewModel(val dataSource: ApiDataSource):ViewModel() {
    //Picture
    private val _upoadProfilePictureResponse= MutableLiveData<BaseResponse<UpdateProfileCommonResponse?>?>()
    var upoadProfilePictureResponse=_upoadProfilePictureResponse

    fun callDashboardGameListResult(file: MultipartBody.Part){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.uploadProfilePictureDataSource(file,UpoadProfilePictureResponseHandler())
        }
    }

    private fun UpoadProfilePictureResponseHandler(): NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?> {
            override fun onResponseSuccess(commonResponse: BaseResponse<UpdateProfileCommonResponse?>?) {
                _upoadProfilePictureResponse.value=commonResponse
            }

            override fun onResponseFailure(
                message: String?,
                commonResponse: BaseResponse<UpdateProfileCommonResponse?>?,
                code: Int?
            ) {
                if (commonResponse==null){
                    val baseResponse= BaseResponse<UpdateProfileCommonResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _upoadProfilePictureResponse.value=baseResponse
                }else{
                    _upoadProfilePictureResponse.value= commonResponse
                }
            }

        }
    }

    //NickName
    private val _updateNickNameResponse= MutableLiveData<BaseResponse<UpdateProfileCommonResponse?>?>()
    var updateNickNameResponse=_updateNickNameResponse

    fun callChangeNickName(changeNickNameRequest: ChangeNickNameRequest){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.changeNickNameDataSource(changeNickNameRequest,updateNickNameResponseHandler())
        }
    }

    private fun updateNickNameResponseHandler(): NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?> {
            override fun onResponseSuccess(commonResponse: BaseResponse<UpdateProfileCommonResponse?>?) {
                _updateNickNameResponse.value=commonResponse
            }

            override fun onResponseFailure(
                message: String?,
                commonResponse: BaseResponse<UpdateProfileCommonResponse?>?,
                code: Int?
            ) {
                if (commonResponse==null){
                    val baseResponse= BaseResponse<UpdateProfileCommonResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _updateNickNameResponse.value=baseResponse
                }else{
                    _updateNickNameResponse.value= commonResponse
                }
            }

        }
    }

    //Password
    private val _updatePasswordResponse= MutableLiveData<BaseResponse<UpdateProfileCommonResponse?>?>()
    var updatePasswordResponse=_updatePasswordResponse

    fun callUpdatePassword(updatePasswordRequest: ChangePasswordRequest){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.changePasswordDataSource(updatePasswordRequest,updatePasswordResponseHandler())
        }
    }

    private fun updatePasswordResponseHandler(): NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?> {
            override fun onResponseSuccess(commonResponse: BaseResponse<UpdateProfileCommonResponse?>?) {
                _updatePasswordResponse.value=commonResponse
            }

            override fun onResponseFailure(
                message: String?,
                commonResponse: BaseResponse<UpdateProfileCommonResponse?>?,
                code: Int?
            ) {
                if (commonResponse==null){
                    val baseResponse= BaseResponse<UpdateProfileCommonResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _updatePasswordResponse.value=baseResponse
                }else{
                    _updatePasswordResponse.value= commonResponse
                }
            }

        }
    }
}