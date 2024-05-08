package com.AG_AP.electroshop.endpoints.models.activity

import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("odata.metadata")
    val odataMetadata: String,
    val value: List<Value>,
    @SerializedName("odata.nextLink")
    val odataNextLink: String,
)