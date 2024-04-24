package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.models.orders.Orders
import retrofit2.http.GET

interface OrdersInterface {
    @GET("b1s/v1/Orders")
    suspend fun getOrders(): Orders
}