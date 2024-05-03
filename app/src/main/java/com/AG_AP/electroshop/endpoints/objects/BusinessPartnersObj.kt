package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.BusinessPartnersInterface
import com.AG_AP.electroshop.endpoints.interfaces.ItemInterface
import com.AG_AP.electroshop.endpoints.models.businessPartners.BusinessPartners
import com.AG_AP.electroshop.endpoints.models.businessPartners.PostBusinesspartner
import com.AG_AP.electroshop.endpoints.models.businessPartners.ReturnBusinessPartner
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient

object BusinessPartnersObj {

    suspend fun getBusinessPartners(urlInt: String): BusinessPartners? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(BusinessPartnersInterface::class.java)
        return try {
            apiService.getBusinessPartners()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    suspend fun postBusinessPartners(urlInt: String, data:PostBusinesspartner): ReturnBusinessPartner? {
        RetrofitClient.baseUrl = urlInt
        val apiService = RetrofitClient.retrofit.create(BusinessPartnersInterface::class.java)
        return try {
            apiService.postBusinessPartners(data)
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }
}