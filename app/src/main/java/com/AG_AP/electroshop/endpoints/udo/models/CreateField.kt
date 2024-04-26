package com.AG_AP.electroshop.endpoints.udo.models

/*data class CreateField(
    val Description: String,
    val Name: String,
    val SubType: String,
    val TableName: String,
    val Type: String,
    val LinkedSystemObject: String
)*/

data class CreateField(
    val Description: String,
    val Name: String,
    val SubType: String,
    val TableName: String,
    val Type: String,
    val Size: Int? = null,
    val LinkedSystemObject: String? = null
)