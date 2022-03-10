package com.co.wewin.utilities.network

import com.co.wewin.model.requests.*
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharAndFastParityAndDiceMyOrderRequest
import com.co.wewin.model.requests.andarBaharGameRequests.BuyOrderAnderBaharAndFastParityAndDiceRequest
import com.co.wewin.model.requests.andarBaharGameRequests.AndarBaharSessionResultRequest
import com.co.wewin.model.responses.*
import com.co.wewin.model.responses.andarBaharResponses.*
import com.co.wewin.model.responses.diceResponse.DiceGameResultArrayResponse
import com.co.wewin.model.responses.diceResponse.DiceNewSessionResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityGameResultArrayResponse
import com.co.wewin.model.responses.fastParityResponses.FastParityNewSessionResponse
import okhttp3.MultipartBody

class ApiDataSource:BaseDataSource() {

      fun loginDataSource(
        loginRequest: LoginRequest?,
        callback: NetworkResponseListener<BaseResponse<LoginResponse?>?>
    ) {
        ApiFactory.API_CALL.login(url(NetworkConstants.loginUser),loginRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun sendOtpDataSource(
        sendOtpRequest: SendOtpRequest?,
        callback: NetworkResponseListener<BaseResponse<SendOtpResponse?>?>
    ) {
        ApiFactory.API_CALL.sendOtp(url(NetworkConstants.sendOtpForLogin),sendOtpRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun registerDataSource(
        registerRequest: RegisterRequest?,
        callback: NetworkResponseListener<BaseResponse<RegisterResponse?>?>
    ) {
        ApiFactory.API_CALL.register(url(NetworkConstants.userRegister),registerRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun andarBaharNewSessionDataSource(
        callback: NetworkResponseListener<BaseResponse<AndarBaharNewSessionResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.andarBaharNewSession(url(NetworkConstants.listAndarBahar))
            .enqueue(CommonResponseHandler(callback))
    }

    fun fastParityNewSessionDataSource(
        callback: NetworkResponseListener<BaseResponse<FastParityNewSessionResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.fastParityNewSession(url(NetworkConstants.listFastParity))
            .enqueue(CommonResponseHandler(callback))
    }

    fun diceNewSessionDataSource(
        callback: NetworkResponseListener<BaseResponse<DiceNewSessionResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.diceNewSession(url(NetworkConstants.listDice))
            .enqueue(CommonResponseHandler(callback))
    }

    fun andarBaharAndFAstParityAndDiceBuyOrderDataSource(
        buyOrderAnderBaharAndFastParityAndDiceRequest: BuyOrderAnderBaharAndFastParityAndDiceRequest?,
        callback: NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.andarBaharBuyOrder(url(NetworkConstants.saveBuy),buyOrderAnderBaharAndFastParityAndDiceRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun diceBuyOrderDataSource(
        buyOrderAnderBaharAndFastParityAndDiceRequest: BuyOrderAnderBaharAndFastParityAndDiceRequest?,
        callback: NetworkResponseListener<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.andarBaharBuyOrder(url(NetworkConstants.diceBuy),buyOrderAnderBaharAndFastParityAndDiceRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun andarBaharResultDeckUnfoldSource(
        andarBaharSessionResultRequest: AndarBaharSessionResultRequest?,
        callback: NetworkResponseListener<BaseResponse<AndarBaharSessionResultResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.andarBaharResultDeckUnfold(url(NetworkConstants.deckAndAnswer),andarBaharSessionResultRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun andarBaharAndFirstParityAndDiceMyOrdersDataSource(
        requestAndFastParityAndDice: AndarBaharAndFastParityAndDiceMyOrderRequest?,
        callback: NetworkResponseListener<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.andarBaharAndFirstParityMyOrders(url(NetworkConstants.listBuy),requestAndFastParityAndDice)
            .enqueue(CommonResponseHandler(callback))
    }
    fun andarBaharGameResultDataSource(
        callback: NetworkResponseListener<BaseResponse<AndarBaharGameResultArrayResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.andarBaharGameResults(url(NetworkConstants.resultListAndarBahar))
            .enqueue(CommonResponseHandler(callback))
    }

    fun fastParityGameResultDataSource(
        callback: NetworkResponseListener<BaseResponse<FastParityGameResultArrayResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.firstParityGameResults(url(NetworkConstants.resultListFastParity))
            .enqueue(CommonResponseHandler(callback))
    }

    fun diceGameResultDataSource(
        callback: NetworkResponseListener<BaseResponse<DiceGameResultArrayResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.diceGameResults(url(NetworkConstants.resultListDice))
            .enqueue(CommonResponseHandler(callback))
    }

    fun uploadProfilePictureDataSource(
        file: MultipartBody.Part,
        callback: NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.uploadProfilePicture(url(NetworkConstants.profileUpload),file)
            .enqueue(CommonResponseHandler(callback))
    }


    fun dashboardGameListDataSource(
        callback: NetworkResponseListener<BaseResponse<DashBoardGameArrayListResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.dashboardGameListResults(url(NetworkConstants.listGames))
            .enqueue(CommonResponseHandler(callback))
    }

    fun changeNickNameDataSource(
        changeNickNameRequest: ChangeNickNameRequest?,
        callback: NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.changeNickName(url(NetworkConstants.updateNickName),changeNickNameRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun changePasswordDataSource(
        changePasswordRequest: ChangePasswordRequest?,
        callback: NetworkResponseListener<BaseResponse<UpdateProfileCommonResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.changePassword(url(NetworkConstants.changePassword),changePasswordRequest)
            .enqueue(CommonResponseHandler(callback))
    }

    fun userDetailsDataSource(
        callback: NetworkResponseListener<BaseResponse<UserProfileDetailsResponse?>?>
    ) {
        ApiFactory.AUTHENTICATED_API_CALL.getUserProfile(url(NetworkConstants.userProfileDetails))
            .enqueue(CommonResponseHandler(callback))
    }
}