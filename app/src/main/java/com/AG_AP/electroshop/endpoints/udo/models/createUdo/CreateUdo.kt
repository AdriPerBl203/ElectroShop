package com.AG_AP.electroshop.endpoints.udo.models.createUdo

data class CreateUdo(
    val Code: String,
    val Name: String,
    val TableName: String,
    val CanCancel: String,
    val CanLog: String,
    val CanCreateDefaultForm: String,
    val CanFind: String,
    val CanClose:String,
    val UserObjectMD_FindColumns: List<UserObjectMDFindColumn>,
    val UserObjectMD_FormColumns: List<UserObjectMDFormColumn>,
    val EnableEnhancedForm: String,
    val MenuItem: String, // tYES
    val MenuCaption:String, // SEICONFIG
    val FatherMenuID: Int, // 2048
    val Position: Int, //12
    val MenuUID:String, // SEICONFIG
)