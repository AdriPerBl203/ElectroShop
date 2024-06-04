package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.ext.realmDictionaryOf
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class InvoiceData : RealmObject {
    var CardCode: String = ""
    var Invoice: RealmList<Invoice> = realmListOf()
}