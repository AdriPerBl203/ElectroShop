package com.AG_AP.electroshop.endpoints

import retrofit2.http.POST

interface LoginService {

    @POST("b1s/v1/Login")
    suspend fun login()
}