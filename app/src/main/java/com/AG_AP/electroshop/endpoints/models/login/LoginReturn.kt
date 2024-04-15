package com.AG_AP.electroshop.endpoints.models.login

import com.google.gson.annotations.SerializedName

data class LoginReturn(
    val SessionId: String,
    val SessionTimeout: Int,
    val Version: String,
    @SerializedName("odata.metadata")
    val odataMetadata: String
)