package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.item.getItems.GetItems
import retrofit2.http.GET

interface ActivityInterface {

    @GET("b1s/v1/Activities")
    suspend fun getActivity(): Activity
}