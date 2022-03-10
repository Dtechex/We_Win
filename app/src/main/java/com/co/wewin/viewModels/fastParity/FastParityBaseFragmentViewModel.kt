package com.co.wewin.viewModels.fastParity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharAndFastParityAndDiceMyOrderArrayResponse
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityGameResultArrayResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FastParityBaseFragmentViewModel(val dataSource: ApiDataSource):ViewModel() {
    private val _fastParityGameResultResponse= MutableLiveData<BaseResponse<FastParityGameResultArrayResponse?>?>()
    var fastParityGameResultResponse=_fastParityGameResultResponse

    fun callFastParityrGameResult(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.fastParityGameResultDataSource(FastParityGameResultResponseHandler())
        }
    }

    private fun FastParityGameResultResponseHandler(): NetworkResponseListener<BaseResponse<FastParityGameResultArrayResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<FastParityGameResultArrayResponse?>?>{
            override fun onResponseSuccess(response: BaseResponse<FastParityGameResultArrayResponse?>?) {
                _fastParityGameResultResponse.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<FastParityGameResultArrayResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<FastParityGameResultArrayResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _fastParityGameResultResponse.value=baseResponse
                }else{
                    _fastParityGameResultResponse.value= response
                }
            }

        }
    }

    private val _fastParityMyOrder= MutableLiveData<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>()
    var fastParityMyOrder=_fastParityMyOrder

    fun callFastParityMyOrders(requestAndFastParityAndDice: AndarBaharAndFastParityAndDiceMyOrderRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharAndFirstParityAndDiceMyOrdersDataSource(requestAndFastParityAndDice,FastParityMyOrderResponseHandler())
        }
    }

    private fun FastParityMyOrderResponseHandler(): NetworkResponseListener<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>{
            override fun onResponseSuccess(arrayResponseAndFastParityAndDice: BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?) {
                _fastParityMyOrder.value=arrayResponseAndFastParityAndDice
            }

            override fun onResponseFailure(
                message: String?,
                responseAndFastParityAndDice: BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?,
                code: Int?
            ) {
                if (responseAndFastParityAndDice==null){
                    var baseResponse=BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _fastParityMyOrder.value=baseResponse
                }else{
                    _fastParityMyOrder.value= responseAndFastParityAndDice
                }
            }

        }
    }

}