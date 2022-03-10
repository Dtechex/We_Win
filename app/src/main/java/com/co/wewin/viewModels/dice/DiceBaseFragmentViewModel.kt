package com.co.wewin.viewModels.dice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import com.co.wewin.model.responses.andarBaharResponses.AndarBaharAndFastParityAndDiceMyOrderArrayResponse
import com.co.wewin.model.responses.BaseResponse
import com.co.wewin.model.responses.diceResponse.DiceGameResultArrayResponse
import com.co.wewin.utilities.network.ApiDataSource
import com.co.wewin.utilities.network.NetworkConstants
import com.co.wewin.utilities.network.NetworkResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DiceBaseFragmentViewModel(val dataSource: ApiDataSource):ViewModel() {
    private val _diceGameResultResponse= MutableLiveData<BaseResponse<DiceGameResultArrayResponse?>?>()
    var diceGameResultResponse=_diceGameResultResponse

    fun callDiceGameResult(){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.diceGameResultDataSource(DiceGameResultResponseHandler())
        }
    }

    private fun DiceGameResultResponseHandler(): NetworkResponseListener<BaseResponse<DiceGameResultArrayResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<DiceGameResultArrayResponse?>?>{
            override fun onResponseSuccess(response: BaseResponse<DiceGameResultArrayResponse?>?) {
                _diceGameResultResponse.value=response
            }

            override fun onResponseFailure(
                message: String?,
                response: BaseResponse<DiceGameResultArrayResponse?>?,
                code: Int?
            ) {
                if (response==null){
                    val baseResponse=BaseResponse<DiceGameResultArrayResponse?>()
                    baseResponse.message=message
                    baseResponse.status= NetworkConstants.Status.FAILED
                    _diceGameResultResponse.value=baseResponse
                }else{
                    _diceGameResultResponse.value= response
                }
            }

        }
    }

    private val _diceMyOrder= MutableLiveData<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>()
    var diceMyOrder=_diceMyOrder

    fun callDiceMyOrders(requestAndFastParityAndDice: AndarBaharAndFastParityAndDiceMyOrderRequest?){
        GlobalScope.launch(Dispatchers.Main) {
            dataSource.andarBaharAndFirstParityAndDiceMyOrdersDataSource(requestAndFastParityAndDice,diceMyOrderResponseHandler())
        }
    }

    private fun diceMyOrderResponseHandler(): NetworkResponseListener<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?> {
        return object :NetworkResponseListener<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>{
            override fun onResponseSuccess(arrayResponseAndFastParityAndDice: BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?) {
                _diceMyOrder.value=arrayResponseAndFastParityAndDice
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
                    _diceMyOrder.value=baseResponse
                }else{
                    _diceMyOrder.value= responseAndFastParityAndDice
                }
            }

        }
    }

}