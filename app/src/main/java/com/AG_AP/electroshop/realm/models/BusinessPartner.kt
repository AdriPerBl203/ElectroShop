package com.AG_AP.electroshop.realm.models

import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class BusinessPartner : RealmObject {

    var idRealm: ObjectId = ObjectId()
    var idFireBase: String? = null
    var CardCode: String = ""
    var CardType: String = ""
    var CardName: String = ""
    var Cellular: String = ""
    var EmailAddress: String = ""
    var SAP: Boolean = false

}
