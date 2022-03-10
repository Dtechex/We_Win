package com.co.wewin.model.responses.diceResponse

class DiceGameResultArrayResponse(
    val results:ArrayList<DiceGameResultResponse?>?
)
class DiceGameResultResponse(
    val gameSession:String?,
    val randomNumber:String?,
)