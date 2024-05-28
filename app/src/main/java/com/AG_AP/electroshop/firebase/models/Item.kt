package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class Item : RealmObject {

    var idRealm: ObjectId = ObjectId()
    var idFireBase: String? = null
    var ItemCode:String = ""
    var itemName: String = ""
    var itemType: String = "I"
    var mainSupplier: String? = ""
    var itemPrice: RealmList<ItemPrice>? = realmListOf()
    var manageSerialNumbers: String = ""
    var autoCreateSerialNumbersOnRelease: String = ""
    var SAP: Boolean = false

}
