package com.co.wewin.viewModels.postLogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.DashBoardGameArrayListResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DashboardGameListViewModle(private val dataSource: ApiDataSource) : ViewModel() {
    private val _dashboardGameListResponse= MutableLiveData<BaseResponse<DashBoardGameArrayListResponse?>?>()
    var dashboardGameListResponse=_dashboardGameListResponse

    fun callDashboardGameListResult(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.dashboardGameListDataSource(DashboardGameListResponseHandler())
        }
    }

    private fun DashboardGameListResponseHandler(): NetworkResponseListener<BaseResponse<DashBoardGameArrayListResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<DashBoardGameArrayListResponse?>?> {
            override fun onResponseSuccess(response: BaseResponse<DashBoardGameArrayListResponse?>?) {
                _dashboardGameListResponse.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<DashBoardGameArrayListResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<DashBoardGameArrayListResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _dashboardGameListResponse.value=baseResponse
                }else{
                    _dashboardGameListResponse.value= response
                }
            }

        }
    }
}