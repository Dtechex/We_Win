package com.co.wewin.viewModels.dice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.andarBaharGameRequests.BuyOrderAnderBaharAndFastParityAndDiceRequest
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.andarBaharResponses.BuyOrderAnderBaharAndFastParityAndDiceResponse
import com.co.wewin.model.responses.diceResponse.DiceNewSessionResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiceViewModel(val dataSource: ApiDataSource):ViewModel() {
    private val _diceNewSessionApiResult=MutableLiveData<BaseResponse<DiceNewSessionResponse?>?>()
    var diceNewSessionApiResult=_diceNewSessionApiResult

    private val _diceOrder= MutableLiveData<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>()
    var diceOrder=_diceOrder


    fun callDiceNewSessionApi(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.diceNewSessionDataSource(DiceNewSessionResponseHandler())
        }
    }

    private fun DiceNewSessionResponseHandler(): NetworkResponseListener<BaseResponse<DiceNewSessionResponse?>?> {
        return object : NetworkResponseListener<BaseResponse<DiceNewSessionResponse?>?> {
            override fun onResponseSuccess(response: BaseResponse<DiceNewSessionResponse?>?) {
                _diceNewSessionApiResult.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<DiceNewSessionResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<DiceNewSessionResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _diceNewSessionApiResult.value=baseResponse
                }else{
                    _diceNewSessionApiResult.value=response!!
                }
            }

        }
    }

    fun callDiceBuyOrderApi(buyOrderAnderBaharAndFastParityAndDiceRequest: BuyOrderAnderBaharAndFastParityAndDiceRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.diceBuyOrderDataSource(buyOrderAnderBaharAndFastParityAndDiceRequest,DiceOrderResponseHandler())
        }
    }

    private fun DiceOrderResponseHandler(): NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>{
            override fun onResponseSuccess(responseBuyAndFastParityAndDice: BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?) {
                _diceOrder.value=responseBuyAndFastParityAndDice
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
                    _diceOrder.value=baseResponse
                }else{
                    _diceOrder.value= responseBuyAndFastParityAndDice
                }
            }

        }
    }


}