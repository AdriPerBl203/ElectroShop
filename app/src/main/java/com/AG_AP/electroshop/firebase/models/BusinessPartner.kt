package com.AG_AP.electroshop.firebase.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class BusinessPartner(
    val CardCode:String ="",
    val CardType:String ="",
    val CardName:String ="",
    val Cellular:String ="",
    val EmailAddress:String ="",
){
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap["CardCode"] = CardCode
        hashMap["CardType"] = CardType
        hashMap["CardName"] = CardName
        hashMap["Cellular"] = Cellular
        hashMap["EmailAddress"] = EmailAddress
        return hashMap
    }
}
