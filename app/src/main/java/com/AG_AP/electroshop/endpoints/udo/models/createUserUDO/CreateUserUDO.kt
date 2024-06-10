package com.AG_AP.electroshop.endpoints.udo.models.createUserUDO

data class CreateUserUDO(
    val Code: String,
    val U_SEI_empleado: Int,
    val U_SEI_pedidoCO: String,
    val U_SEI_pedidoCl: String,
    val U_SEI_actividad: String,
    val U_SEI_articulo: String,
    val U_SEI_name: String,
    val U_SEI_password: String
)