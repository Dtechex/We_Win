package com.co.wewin.viewModels.postLogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.UserProfileDetailsResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyProfileViewModel(private val dataSource: ApiDataSource):ViewModel() {
    private val _userProfileDetailsResponse= MutableLiveData<BaseResponse<UserProfileDetailsResponse?>?>()
    var userProfileDetailsResponse=_userProfileDetailsResponse

    fun callUserDetails(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.userDetailsDataSource(userDetailsResponseHandler())
        }
    }

    private fun userDetailsResponseHandler(): NetworkResponseListener<BaseResponse<UserProfileDetailsResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<UserProfileDetailsResponse?>?> {
            override fun onResponseSuccess(commonResponse: BaseResponse<UserProfileDetailsResponse?>?) {
                _userProfileDetailsResponse.value=commonResponse
            }

            override fun onResponseFailure(
                message: String?,
                commonResponse: BaseResponse<UserProfileDetailsResponse?>?,
                code: Int?
            ) {
                if (commonResponse==null){
                    val baseResponse= BaseResponse<UserProfileDetailsResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _userProfileDetailsResponse.value=baseResponse
                }else{
                    _userProfileDetailsResponse.value= commonResponse
                }
            }

        }
    }
}