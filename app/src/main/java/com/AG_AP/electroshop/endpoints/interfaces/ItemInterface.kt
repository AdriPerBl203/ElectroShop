package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import retrofit2.http.GET
import retrofit2.http.Query

interface ItemInterface {
    @GET("b1s/v1/Items")
    suspend fun getItems(): GetItems

    @GET("b1s/v1/Items")
    suspend fun getItemsExten(@Query("\$skip") skip: Int): GetItems
}