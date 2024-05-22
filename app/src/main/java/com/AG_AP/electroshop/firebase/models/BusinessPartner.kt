package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

data class BusinessPartner(
    val idFireBase: String? = null,
    val CardCode:String ="",
    val CardType:String ="",
    val CardName:String ="",
    val Cellular:String ="",
    val EmailAddress:String ="",

    val SAP: Boolean
) : RealmObject {
    fun toHashMap(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        idFireBase?.let { hashMap["idFireBase"] = it }
        hashMap["CardCode"] = CardCode
        hashMap["CardType"] = CardType
        hashMap["CardName"] = CardName
        hashMap["Cellular"] = Cellular
        hashMap["EmailAddress"] = EmailAddress

        hashMap["SAP"] = SAP
        return hashMap
    }
}
