package com.AG_AP.electroshop.endpoints.udo.models.createFieldChetado

data class PostCreateField(
    val Description: String,
    val Name: String,
    val SubType: String,
    val TableName: String,
    val Type: String,
    val Size: Int? = null,
    val DefaultValue: String,
    val ValidValuesMD: List<ValidValuesMD>
)