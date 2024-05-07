package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.businessPartners.PostBusinesspartner
import com.AG_AP.electroshop.endpoints.models.businessPartners.ReturnBusinessPartner
import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrdersInterface {
    @GET("b1s/v1/Orders")
    suspend fun getOrders(): Orders

    @POST("b1s/v1/Orders")
    suspend fun postOrders(@Body data: PostOrder)
}