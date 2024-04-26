package com.AG_AP.electroshop.endpoints.udo.models.createUdo

data class UserObjectMDFormColumn(
    val Code: String,
    val Editable: String,
    val FormColumnAlias: String,
    val FormColumnDescription: String,
    val FormColumnNumber: Int,
    val SonNumber: Int
)