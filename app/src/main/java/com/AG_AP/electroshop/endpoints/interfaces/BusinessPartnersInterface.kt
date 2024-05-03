package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.businessPartners.BusinessPartners
import com.AG_AP.electroshop.endpoints.models.businessPartners.PostBusinesspartner
import com.AG_AP.electroshop.endpoints.models.businessPartners.ReturnBusinessPartner
import com.AG_AP.electroshop.endpoints.models.login.Login
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BusinessPartnersInterface {
    @GET("b1s/v1/BusinessPartners")
    suspend fun getBusinessPartners(): BusinessPartners

    @POST("b1s/v1/BusinessPartners")
    suspend fun postBusinessPartners(@Body data: PostBusinesspartner): ReturnBusinessPartner
}