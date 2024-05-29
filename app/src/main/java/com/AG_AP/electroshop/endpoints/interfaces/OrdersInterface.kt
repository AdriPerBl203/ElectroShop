package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OrdersInterface {

    @GET("b1s/v1/Orders?\$filter=DocumentStatus eq 'bost_Open'")
    @Headers("Prefer: odata.maxpagesize=0")
    suspend fun getOrders(): Orders

    @GET("b1s/v1/Orders?\$filter=DocumentStatus eq 'bost_Open'&")
    suspend fun getOrdersExten(@Query("\$skip") skip: Int): Orders

    @POST("b1s/v1/Orders")
    suspend fun postOrders(@Body data: PostOrder)
}