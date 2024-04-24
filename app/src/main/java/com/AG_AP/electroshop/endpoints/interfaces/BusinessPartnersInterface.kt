package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.businessPartners.BusinessPartners
import retrofit2.http.GET

interface BusinessPartnersInterface {
    @GET("b1s/v1/BusinessPartners")
    suspend fun getBusinessPartners(): BusinessPartners
}