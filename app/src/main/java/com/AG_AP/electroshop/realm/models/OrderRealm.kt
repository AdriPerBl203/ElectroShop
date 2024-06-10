package com.AG_AP.electroshop.realm.models


import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class OrderRealm : RealmObject {

    var idRealm: ObjectId = ObjectId()
    var idFireBase: String? = null
    var DocNum: Int = 0
    var CardCode: String = ""
    var CardName: String? = ""
    var DocDate: String = ""
    var DocDueDate: String = ""
    var TaxDate: String = ""
    var DiscountPercent: Double = 0.0
    var DocumentLines: RealmList<DocumentLineRealm> = realmListOf()
    var Slpcode: String = "-1"
    var SAP: Boolean = false
    var SalesPersonCode:Int = -1
}
