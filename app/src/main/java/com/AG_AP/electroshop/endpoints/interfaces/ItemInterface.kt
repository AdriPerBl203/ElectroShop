package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import retrofit2.http.GET

interface ItemInterface {
    @GET("b1s/v1/Items")
    suspend fun getItems(): GetItems
}