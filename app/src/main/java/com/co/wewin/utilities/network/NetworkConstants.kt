package com.co.wewin.utilities.network

object NetworkConstants {
//    const val BASE_URL="http://192.168.1.127:5000/"
    const val BASE_URL="https://wewins.herokuapp.com/"

    object Status {
        const val FAILED = false
        const val SUCCESS = true
    }

    //register fragment
    val userRegister="userRegister"
    val sendOtpForLogin="sendOtpForLogin"

    //login fragment
    val loginUser="loginUser"

    //andarBahar Fragment
    val listAndarBahar="listAndarBahar"
    val saveBuy="saveBuy"
    val deckAndAnswer="deckAndAnswer"
    val resultListAndarBahar="resultListAndarBahar"

    //parity and anadhar bahar
    val listBuy="listBuy"

    //dashboard fragment
    val listGames="listGames"

    //fastParity
    val listFastParity="listFastParity"
    val resultListFastParity="resultListFastParity"
    val parityBuy="parityBuy"

    //dice
    val listDice="listDice"
    val diceBuy="diceBuy"
    val resultListDice="resultListDice"

    //profile
    val profileUpload="profileUpload"
    val updateNickName="updateNickName"
    val changePassword="changePassword"
    val userProfileDetails="userProfileDetails"
}