package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.activity.Activity
import com.AG_AP.electroshop.endpoints.models.activity.PostActivity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ActivityInterface {

    @GET("b1s/v1/Activities")
    suspend fun getActivity(): Activity

    @GET("b1s/v1/Activities")
    suspend fun getActivitiesExten(@Query("\$skip") skip: Int): Activity
    @POST("b1s/v1/Activities")
    suspend fun postActivity(@Body data: PostActivity)
}