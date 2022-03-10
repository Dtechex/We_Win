package com.co.wewin.model.responses.andarBaharResponses

class AndarBaharSessionResultResponse(
    val answer:String?,
    val cardShape:String?,
    val cardNo:String?,
    val deck:Array<AndarBaharSessionResultDeckArray?>?
){
    inner class AndarBaharSessionResultDeckArray(
        val cardShape:String?,
        val cardNo:String?,
    )
}
