package com.AG_AP.electroshop.realm.models

import io.realm.kotlin.types.RealmObject

class InvoiceData : RealmObject {
    var CardCode: String = ""
    var DocNum: Int = -1
    //Esto no lo quería hacer me ha obligado Aaron
    var DocEntry: Long = -1
    var Base64String: String = ""
}