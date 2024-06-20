package com.AG_AP.electroshop.endpoints.objects

import com.AG_AP.electroshop.endpoints.interfaces.ExportToPDF
import com.AG_AP.electroshop.endpoints.models.exportToPDF.DataPostExportToPDF
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
}