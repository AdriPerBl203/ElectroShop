package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore

class Item : RealmObject {
    var idFireBase: String? = null
    var ItemCode:String = ""
    var itemName: String = ""
    var itemType: String = "I"
    var mainSupplier: String? = ""
    @Ignore
    var itemPrice: List<Price>? = listOf()
    var manageSerialNumbers: String = ""
    var autoCreateSerialNumbersOnRelease: String = ""
    var SAP: Boolean = false

}
