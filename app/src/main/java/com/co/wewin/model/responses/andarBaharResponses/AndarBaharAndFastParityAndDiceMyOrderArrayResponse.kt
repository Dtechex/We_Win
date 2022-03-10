package com.co.wewin.model.responses.andarBaharResponses

class AndarBaharAndFastParityAndDiceMyOrderArrayResponse(
    val orders:ArrayList<AndarBaharMyOrderResponse?>?
)
class AndarBaharMyOrderResponse(
    val userId:String?,
    val amount:String?,
    val option:String?,
    val gameSession:String?,
    val avatar:String?,
    val gameName:String?,
    val createdAt:String?,
    val updatedAt:String?,
)
