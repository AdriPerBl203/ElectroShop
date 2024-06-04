package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.ext.realmDictionaryOf
import io.realm.kotlin.types.RealmMap
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore

class InvoiceData : RealmObject {
    //TODO VOLVER AQUI A REALIZAR
    var CardCode: String = ""
    @Ignore
    var Invoice: RealmMap<String, String> = realmDictionaryOf()
}