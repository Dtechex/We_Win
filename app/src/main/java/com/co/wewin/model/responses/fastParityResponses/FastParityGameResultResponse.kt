package com.co.wewin.model.responses.fastParityResponses

class FastParityGameResultArrayResponse(
    val results:ArrayList<FastParityGameResultResponse?>?
)
class FastParityGameResultResponse(
    val gameSession:String?,
    val winColor:String?,
    val winNumber:String?
)