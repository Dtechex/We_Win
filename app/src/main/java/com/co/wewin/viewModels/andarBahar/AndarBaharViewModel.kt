package com.co.wewin.viewModels.andarBahar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.andarBaharGameRequests.BuyOrderAnderBaharAndFastParityAndDiceRequest
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharSessionResultRequest
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharNewSessionResponse
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharSessionResultResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AndarBaharViewModel(val dataSource: ApiDataSource):ViewModel() {
    private val _andarBaharNewSessionApiResult= MutableLiveData<BaseResponse<AndarBaharNewSessionResponse?>?>()
    var andarBaharNewSessionApiResult=_andarBaharNewSessionApiResult

    private val _andarBaharOrder= MutableLiveData<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>()
    var andarBaharBuyOrder=_andarBaharOrder

    private val _andarBaharResultDeckUnfold= MutableLiveData<BaseResponse<AndarBaharSessionResultResponse?>?>()
    var andarBaharResultDeckUnfold=_andarBaharResultDeckUnfold

    fun callAndarBaharNewSessionApi(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharNewSessionDataSource(AndarBaharNewSessionResponseHandler())
        }
    }

    private fun AndarBaharNewSessionResponseHandler(): NetworkResponseListener<BaseResponse<AndarBaharNewSessionResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<AndarBaharNewSessionResponse?>?>{
            override fun onResponseSuccess(response: BaseResponse<AndarBaharNewSessionResponse?>?) {
                _andarBaharNewSessionApiResult.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<AndarBaharNewSessionResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<AndarBaharNewSessionResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _andarBaharNewSessionApiResult.value=baseResponse
                }else{
                    _andarBaharNewSessionApiResult.value=response!!
                }
            }

        }
    }

    fun callAndarBaharBuyOrderApi(buyOrderAnderBaharAndFastParityAndDiceRequest: BuyOrderAnderBaharAndFastParityAndDiceRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharAndFAstParityAndDiceBuyOrderDataSource(buyOrderAnderBaharAndFastParityAndDiceRequest,AndarBaharOrderResponseHandler())
        }
    }

    private fun AndarBaharOrderResponseHandler(): NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>{
            override fun onResponseSuccess(responseBuyAndFastParityAndDice: BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?) {
                _andarBaharOrder.value=responseBuyAndFastParityAndDice
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
                    _andarBaharOrder.value=baseResponse
                }else{
                    _andarBaharOrder.value=responseBuyAndFastParityAndDice!!
                }
            }

        }
    }


    fun callAndarBaharResultDeckUnfoldApi(andarBaharSessionResultRequest: AndarBaharSessionResultRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharResultDeckUnfoldSource(andarBaharSessionResultRequest,AndarBaharUpdateResponseHandler())
        }
    }

    private fun AndarBaharUpdateResponseHandler(): NetworkResponseListener<BaseResponse<AndarBaharSessionResultResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<AndarBaharSessionResultResponse?>?>{
            override fun onResponseSuccess(sessionResultResponse: BaseResponse<AndarBaharSessionResultResponse?>?) {
                _andarBaharResultDeckUnfold.value=sessionResultResponse
            }

            override fun onResponseFailure(
                message: String?,
                sessionResultResponse: BaseResponse<AndarBaharSessionResultResponse?>?,
                code: Int?
            ) {
                if (sessionResultResponse==null){
                    var baseResponse=BaseResponse<AndarBaharSessionResultResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _andarBaharResultDeckUnfold.value=baseResponse
                }else{
                    _andarBaharResultDeckUnfold.value=sessionResultResponse!!
                }
            }

        }
    }


}