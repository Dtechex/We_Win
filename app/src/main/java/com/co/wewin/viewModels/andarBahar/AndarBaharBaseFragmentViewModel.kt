package com.co.wewin.viewModels.andarBahar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharGameResultArrayResponse
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharAndFastParityAndDiceMyOrderArrayResponse
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AndarBaharBaseFragmentViewModel(val dataSource: ApiDataSource):ViewModel() {
    private val _andarBaharGameResultResponse= MutableLiveData<BaseResponse<AndarBaharGameResultArrayResponse?>?>()
    var andarBaharGameResultResponse=_andarBaharGameResultResponse

    fun callAndarBaharGameResult(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharGameResultDataSource(AndarBaharGameResultResponseHandler())
        }
    }

    private fun AndarBaharGameResultResponseHandler(): NetworkResponseListener<BaseResponse<AndarBaharGameResultArrayResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<AndarBaharGameResultArrayResponse?>?>{
            override fun onResponseSuccess(response: BaseResponse<AndarBaharGameResultArrayResponse?>?) {
                _andarBaharGameResultResponse.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<AndarBaharGameResultArrayResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<AndarBaharGameResultArrayResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _andarBaharGameResultResponse.value=baseResponse
                }else{
                    _andarBaharGameResultResponse.value= response
                }
            }

        }
    }

    private val _andarBaharMyOrder= MutableLiveData<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>()
    var andarBaharMyOrder=_andarBaharMyOrder

    fun callAndarBaharMyOrders(requestAndFastParityAndDice: AndarBaharAndFastParityAndDiceMyOrderRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharAndFirstParityAndDiceMyOrdersDataSource(requestAndFastParityAndDice,AndarBaharMyOrderResponseHandler())
        }
    }

    private fun AndarBaharMyOrderResponseHandler(): NetworkResponseListener<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>{
            override fun onResponseSuccess(arrayResponseAndFastParityAndDice: BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?) {
                _andarBaharMyOrder.value=arrayResponseAndFastParityAndDice
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
                    _andarBaharMyOrder.value=baseResponse
                }else{
                    _andarBaharMyOrder.value= responseAndFastParityAndDice
                }
            }

        }
    }

}