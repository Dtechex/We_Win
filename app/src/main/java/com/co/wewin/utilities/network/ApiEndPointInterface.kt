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
import retrofit2.Call
import retrofit2.http.*

import retrofit2.http.POST

import retrofit2.http.Multipart





interface ApiEndPointInterface {

    @POST
     fun login(@Url url:String,@Body request: LoginRequest?) : Call<BaseResponse<LoginResponse?>?>

    @POST
    fun sendOtp(@Url url:String,@Body request: SendOtpRequest?) : Call<BaseResponse<SendOtpResponse?>>

    @POST
    fun register(@Url url:String,@Body request: RegisterRequest?) : Call<BaseResponse<RegisterResponse?>?>

    @POST
    fun andarBaharNewSession(@Url url:String) : Call<BaseResponse<AndarBaharNewSessionResponse?>?>

    @POST
    fun andarBaharBuyOrder(@Url url:String, @Body requestBuyAndFastParityAndDice: BuyOrderAnderBaharAndFastParityAndDiceRequest?) : Call<BaseResponse<BuyOrderAnderBaharAndFastParityAndDiceResponse?>?>

    @POST
    fun andarBaharResultDeckUnfold(@Url url:String, @Body sessionResultRequest: AndarBaharSessionResultRequest?) : Call<BaseResponse<AndarBaharSessionResultResponse?>?>

    @POST
    fun andarBaharAndFirstParityMyOrders(@Url url:String, @Body requestAndFastParityAndDice: AndarBaharAndFastParityAndDiceMyOrderRequest?) : Call<BaseResponse<AndarBaharAndFastParityAndDiceMyOrderArrayResponse?>?>

    @POST
    fun andarBaharGameResults(@Url url:String) : Call<BaseResponse<AndarBaharGameResultArrayResponse?>?>

    @POST
    fun dashboardGameListResults(@Url url:String) : Call<BaseResponse<DashBoardGameArrayListResponse?>?>

    @POST
    fun fastParityNewSession(@Url url:String) : Call<BaseResponse<FastParityNewSessionResponse?>?>

    @POST
    fun diceNewSession(@Url url:String) : Call<BaseResponse<DiceNewSessionResponse?>?>

    @POST
    fun firstParityGameResults(@Url url:String) : Call<BaseResponse<FastParityGameResultArrayResponse?>?>

    @POST
    fun diceGameResults(@Url url:String) : Call<BaseResponse<DiceGameResultArrayResponse?>?>

    @Multipart
    @POST
    fun uploadProfilePicture(@Url url:String, @Part filePart: MultipartBody.Part): Call<BaseResponse<UpdateProfileCommonResponse?>?>

    @POST
    fun changeNickName(@Url url:String, @Body changeNickNameRequest: ChangeNickNameRequest?) : Call<BaseResponse<UpdateProfileCommonResponse?>?>

    @POST
    fun changePassword(@Url url:String, @Body changePasswordRequest: ChangePasswordRequest?) : Call<BaseResponse<UpdateProfileCommonResponse?>?>

    @POST
    fun getUserProfile(@Url url:String) : Call<BaseResponse<UserProfileDetailsResponse?>?>

}