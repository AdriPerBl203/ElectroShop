package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import com.AG_AP.electroshop.endpoints.models.purchaseOrders.PurchaseOrders
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PurchaseOrdersInteface {
    @GET("b1s/v1/PurchaseOrders")
    suspend fun getPurchaseOrders(): PurchaseOrders

    @GET("b1s/v1/PurchaseOrders")
    suspend fun getPurchaseOrdersExten(@Query("\$skip") skip: Int): PurchaseOrders

    @POST("b1s/v1/PurchaseOrders")
    suspend fun postPurchaseOrders(@Body data: PostOrder)
}