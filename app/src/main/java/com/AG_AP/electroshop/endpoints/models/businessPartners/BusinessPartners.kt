package com.AG_AP.electroshop.endpoints.models.businessPartners

import com.google.gson.annotations.SerializedName

data class BusinessPartners(
    @SerializedName("odata.metadata")
    val odataMetadata: String,
    @SerializedName("odata.nextLink")
    val odataNextLink: String,
    val value: List<Value>
)