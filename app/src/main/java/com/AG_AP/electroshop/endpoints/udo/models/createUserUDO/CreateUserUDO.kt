package com.AG_AP.electroshop.endpoints.udo.models.createUserUDO

data class CreateUserUDO(
    val Code: String,
    val U_Empleado: Int,
    val U_PedidoCO: String,
    val U_PedidoCl: String,
    val U_actividad: String,
    val U_articulo: String,
    val U_name: String,
    val U_password: String
)