package com.co.wewin.model.responses

class DashBoardGameArrayListResponse(
    val gameList:ArrayList<DashBoardGameListResponse?>?
)
class DashBoardGameListResponse(
    val _id:String?,
    val gameImage:String?,
    val gameName:String?,
    val status:String?,
    val createdAt:String?
)