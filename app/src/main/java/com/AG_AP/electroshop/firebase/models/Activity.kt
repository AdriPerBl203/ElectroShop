package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject


data class Activity(
    var idFireBase: String? = null,
    var nota:String,
    var ActivityDate:String,
    var ActivityTime:String,
    var CardCode:String,
    var EndTime:String,
    var Action:String,
    var Tel:String,
    var ClgCode:String,
    var Priority:String,
    var U_SEIPEDIDOCOMPRAS:Int?,
    var U_SEIPEDIDOCLIENTE:Int?,
    var SAP:Boolean,
) : RealmObject {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        idFireBase?.let { hashMap["idFireBase"] = it }
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
        hashMap["SAP"] = SAP
        return hashMap
    }
}
