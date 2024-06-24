package com.AG_AP.electroshop.endpoints.udo.objects

import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient
import com.AG_AP.electroshop.endpoints.udo.interfaces.UDOInterface
import com.AG_AP.electroshop.endpoints.udo.models.CreateField
import com.AG_AP.electroshop.endpoints.udo.models.CreateTable
import com.AG_AP.electroshop.endpoints.udo.models.createFieldChetado.PostCreateField
import com.AG_AP.electroshop.endpoints.udo.models.createUdo.CreateUdo
import com.AG_AP.electroshop.endpoints.udo.models.createUserUDO.CreateUserUDO
import com.AG_AP.electroshop.endpoints.udo.models.createUserUDO.CreateUserUdoRes
import com.AG_AP.electroshop.endpoints.udo.models.getUserUdo.SeiConfigUser
import retrofit2.Response

object UDOobj {

    val apiService = RetrofitClient.retrofit.create(UDOInterface::class.java)
    suspend fun createTable(urlInt: String): Boolean {
        RetrofitClient.baseUrl = urlInt
        return try {
            val data: CreateTable = CreateTable("SEICONFIG","SEICONFIG","bott_MasterData")
            apiService.createTable(data)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    suspend fun createField(urlInt: String, data: CreateField): Boolean {
        RetrofitClient.baseUrl = urlInt
        return try {
            apiService.createField(data)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    suspend fun createFieldChetado(urlInt: String, data: PostCreateField): Boolean {
        RetrofitClient.baseUrl = urlInt
        return try {
            apiService.createFieldChetado(data)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    suspend fun createUDO(urlInt: String, data: CreateUdo): Boolean {
        RetrofitClient.baseUrl = urlInt
        return try {
            apiService.createUDO(data)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    suspend fun createuserSEICONFIG(urlInt: String, data: CreateUserUDO): CreateUserUdoRes? {
        RetrofitClient.baseUrl = urlInt
        return try {
            apiService.createUSerSEICONFIG(data)
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

    suspend fun getUserTableUDO(urlInt: String): SeiConfigUser? {
        RetrofitClient.baseUrl = urlInt
        return try {
            apiService.getUsers()
        } catch (e: Exception) {
            println(e.message)
            null
        }
    }

}