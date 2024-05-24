package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class PriceListRealm : RealmObject {
    var Active: String = ""
    var BasePriceList: String = ""
    var PriceListNo : String = ""
    var PriceListName:String = ""
}