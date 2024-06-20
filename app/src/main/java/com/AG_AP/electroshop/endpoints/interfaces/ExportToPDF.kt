package com.AG_AP.electroshop.endpoints.interfaces

import com.AG_AP.electroshop.endpoints.models.exportToPDF.DataPostExportToPDF
import com.AG_AP.electroshop.endpoints.models.item.postItems.PostItem
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ExportToPDF {
    @POST("rs/v1/ExportPDFData")
    suspend fun postExportToPDF(
        @Query("DocCode") docCode: String,
        @Body data: DataPostExportToPDF
    ): Response<ResponseBody>
}