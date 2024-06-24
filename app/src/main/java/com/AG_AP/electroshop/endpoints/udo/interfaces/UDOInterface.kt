package com.AG_AP.electroshop.endpoints.udo.interfaces

import com.AG_AP.electroshop.endpoints.udo.models.getUserUdo.SeiConfigUser
import com.AG_AP.electroshop.endpoints.udo.models.CreateField
import com.AG_AP.electroshop.endpoints.udo.models.CreateTable
import com.AG_AP.electroshop.endpoints.udo.models.createFieldChetado.PostCreateField
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.CreateUdo
import com.AG_AP.electroshop.endpoints.udo.models.createUserUDO.CreateUserUDO
import com.AG_AP.electroshop.endpoints.udo.models.createUserUDO.CreateUserUdoRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UDOInterface {

    @POST("b1s/v1/UserTablesMD")
    suspend fun createTable(@Body data: CreateTable)

    @POST("b1s/v1/UserFieldsMD")
    suspend fun createField(@Body data: CreateField)

    @POST("b1s/v1/UserFieldsMD")
    suspend fun createFieldChetado(@Body data: PostCreateField)

    @POST("b1s/v1/UserObjectsMD")
    suspend fun createUDO(@Body data: CreateUdo)

    @POST("b1s/v1/SEICONFIG")
    suspend fun createUSerSEICONFIG(@Body data: CreateUserUDO): CreateUserUdoRes

    @GET("b1s/v1/SEICONFIG")
    suspend fun getUsers(): SeiConfigUser?
}