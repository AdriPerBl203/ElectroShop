package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class BusinessPartner : RealmObject {

    var idFireBase: String? = null
    var CardCode: String = ""
    var CardType: String = ""
    var CardName: String = ""
    var Cellular: String = ""
    var EmailAddress: String = ""
    var SAP: Boolean = false

}
