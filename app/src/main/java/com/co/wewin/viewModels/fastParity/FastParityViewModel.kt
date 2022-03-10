package com.co.wewin.viewModels.fastParity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.andarBaharGameRequests.BuyOrderAnderBaharAndFastParityAndDiceRequest
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityNewSessionResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FastParityViewModel(val dataSource: ApiDataSource):ViewModel() {
    private val _fastParityNewSessionApiResult= MutableLiveData<BaseResponse<FastParityNewSessionResponse?>?>()
    var fastParityNewSessionApiResult=_fastParityNewSessionApiResult

    private val _fastParityOrder= MutableLiveData<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>()
    var fastParityOrder=_fastParityOrder

    fun callFastParityNewSessionApi(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.fastParityNewSessionDataSource(FastParityNewSessionResponseHandler())
        }
    }

    private fun FastParityNewSessionResponseHandler(): NetworkResponseListener<BaseResponse<FastParityNewSessionResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<FastParityNewSessionResponse?>?> {
            override fun onResponseSuccess(response: BaseResponse<FastParityNewSessionResponse?>?) {
                _fastParityNewSessionApiResult.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<FastParityNewSessionResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<FastParityNewSessionResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _fastParityNewSessionApiResult.value=baseResponse
                }else{
                    _fastParityNewSessionApiResult.value=response!!
                }
            }

        }
    }

    fun callFastParityBuyOrderApi(buyOrderAnderBaharAndFastParityAndDiceAndFastParityAndDiceRequest: BuyOrderAnderBaharAndFastParityAndDiceRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharAndFAstParityAndDiceBuyOrderDataSource(buyOrderAnderBaharAndFastParityAndDiceAndFastParityAndDiceRequest,FastParityOrderResponseHandler())
        }
    }

    private fun FastParityOrderResponseHandler(): NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>{
            override fun onResponseSuccess(responseBuyAndFastParityAndDice: BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?) {
                _fastParityOrder.value=responseBuyAndFastParityAndDice
            }

            override fun onResponseFailure(
                message: String?,
                responseBuyAndFastParityAndDice: BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?,
                code: Int?
            ) {
                if (responseBuyAndFastParityAndDice==null){
                    var baseResponse=BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _fastParityOrder.value=baseResponse
                }else{
                    _fastParityOrder.value= responseBuyAndFastParityAndDice
                }
            }

        }
    }
}