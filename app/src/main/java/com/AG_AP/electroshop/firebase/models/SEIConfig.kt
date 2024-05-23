package com.AG_AP.electroshop.firebase.models

import io.realm.kotlin.types.RealmObject

class SEIConfig : RealmObject {
    var Code: Int = 0
    var U_Empleado: Int = 0
    var U_name: String = ""
    var U_password: String = ""
    var U_articulo: String = ""
    var U_actividad: String = ""
    var U_PedidoCI: String = ""
    var U_PedidoCO: String = ""
}
