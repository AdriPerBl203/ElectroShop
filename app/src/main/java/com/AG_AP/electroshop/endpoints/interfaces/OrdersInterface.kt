package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.businessPartners.PostBusinesspartner
import com.AG_AP.electroshop.endpoints.models.businessPartners.ReturnBusinessPartner
import com.AG_AP.electroshop.endpoints.models.orders.Orders
import com.AG_AP.electroshop.endpoints.models.orders.orderFilterClientAndDocument.OrdersFilterClientAndDocumentLines
import com.AG_AP.electroshop.endpoints.models.orders.post.PostOrder
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface OrdersInterface {
    @GET("b1s/v1/Orders")
    suspend fun getOrders(): Orders

    @GET("b1s/v1/Orders")
    suspend fun getOrdersExten(@Query("\$skip") skip: Int): Orders

    @POST("b1s/v1/Orders")
    suspend fun postOrders(@Body data: PostOrder)

    @Headers("Prefer: odata.maxpagesize=0")
    @GET("b1s/v1/Orders?\$select=CardCode, DocumentLines, DocDueDate&\$orderby=CardCode asc, DocDueDate desc")
    suspend fun getClientAndDocumentLinesFilter(): OrdersFilterClientAndDocumentLines
}