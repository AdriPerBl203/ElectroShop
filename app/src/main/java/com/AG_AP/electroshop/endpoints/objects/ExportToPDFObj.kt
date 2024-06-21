package com.AG_AP.electroshop.endpoints.objects

import android.util.Log
import com.AG_AP.electroshop.endpoints.interfaces.ExportToPDF
import com.AG_AP.electroshop.endpoints.interfaces.LoginInterface
import com.AG_AP.electroshop.endpoints.models.exportToPDF.DataPostExportToPDF
import com.AG_AP.electroshop.endpoints.models.login.Login
import com.AG_AP.electroshop.endpoints.models.login.LoginReturnGateway
import com.AG_AP.electroshop.endpoints.retrofit.RetrofitClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

object ExportToPDFObj {

    //TODO QUEDA TRAERSE EL BODY DE LA RESPUESTA
    suspend fun postExporToPDF(data: DataPostExportToPDF,codePDF:String): Response<ResponseBody>? {
        val apiService = RetrofitClient.retrofitGetaway.create(ExportToPDF::class.java)
        return try {
            apiService.postExportToPDF(codePDF,data)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun checkExporToPDF(codePDF:String): Boolean {
        val apiService = RetrofitClient.retrofitGetaway.create(ExportToPDF::class.java)
        val response = apiService.checkExportToPDF(codePDF)

        return if (response.isSuccessful) {
            val body = response.body()
            val content = body?.string()
            Log.d("content", content.toString())
            if(content.toString() != "{}"){
                true
            }else{
                false
            }
        } else {
            false
        }
    }
}