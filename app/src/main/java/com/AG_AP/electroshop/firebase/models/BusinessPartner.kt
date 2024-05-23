package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class BusinessPartner(
    var idFireBase: String? = null,
    var CardCode:String ="",
    var CardType:String ="",
    var CardName:String ="",
    var Cellular:String ="",
    var EmailAddress:String ="",
    var SAP: Boolean
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
