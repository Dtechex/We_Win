package com.co.wewin.model.responses

class RegisterResponse(
    val acknowledged:Boolean?,
    val modifiedCount:Int?,
    val upsertedId:String?,
    val upsertedCount:Int?,
    val matchedCount:Int?
)