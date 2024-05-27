package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class Activity : RealmObject {

    var idFireBase: String? = null
    var nota: String = ""
    var ActivityDate: String = ""
    var ActivityTime: String = ""
    var CardCode: String = ""
    var EndTime: String = ""
    var Action: String = ""
    var Tel: String = ""
    var ClgCode: String = ""
    var Priority: String = ""
    var U_SEIPEDIDOCOMPRAS: Int? = null
    var U_SEIPEDIDOCLIENTE: Int? = null
    var SAP: Boolean = false

}
