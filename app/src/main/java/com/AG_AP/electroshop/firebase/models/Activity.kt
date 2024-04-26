package com.AG_AP.electroshop.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Activity(
    val nota:String,
    val ActivityDate:String,
    val ActivityTime:String,
    val CardCode:String,
    val EndTime:String,
    val Action:String,
    val Tel:String,
    val ClgCode:String,
    val Priority:String,
    val U_SEIPEDIDOCOMPRAS:Int?,
    val U_SEIPEDIDOCLIENTE:Int?,
){
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["nota"] = nota
        hashMap["ActivityDate"] = ActivityDate
        hashMap["ActivityTime"] = ActivityTime
        hashMap["CardCode"] = CardCode
        hashMap["EndTime"] = EndTime
        hashMap["Tel"] = Tel
        hashMap["ClgCode"] = ClgCode
        hashMap["Priority"] = Priority
        hashMap["Action"] = Action
        U_SEIPEDIDOCOMPRAS?.let { hashMap["U_SEIPEDIDOCOMPRAS"] = it }
        U_SEIPEDIDOCLIENTE?.let { hashMap["U_SEIPEDIDOCLIENTE"] = it }
        return hashMap
    }
}