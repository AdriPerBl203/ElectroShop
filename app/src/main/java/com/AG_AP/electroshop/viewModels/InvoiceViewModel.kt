package com.AG_AP.electroshop.viewModels

import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.ParcelFileDescriptor
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.firebase.InvoiceDataCRUD
import com.AG_AP.electroshop.firebase.models.InvoiceData
import com.AG_AP.electroshop.functions.ObjectContext
import com.AG_AP.electroshop.uiState.InvoiceUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Base64

class InvoiceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InvoiceUiState())
    val uiState: StateFlow<InvoiceUiState> = _uiState.asStateFlow()

    init {
        searchData()
    }


    /**
     * Method that searches for new invoice in the database and updates the list inside the uiState
     */
    private fun searchData() {
        var mutableList: MutableList<InvoiceData?> = mutableListOf()
        InvoiceDataCRUD.getAllObject {
            mutableList = it

        }

        _uiState.update { currentState ->
            currentState.copy(
                BusinessPartnerWithInvoiceList = mutableList
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun replaceData(item: InvoiceData?) {
        if (item != null) {
            // Actualizamos el base64
            _uiState.update { currentState ->
                currentState.copy(
                    Base64String = item.Base64String
                )
            }

            val pdfFile =
                decodeBase64ToPdfFile(ObjectContext.context.cacheDir, _uiState.value.Base64String)
            val pdfRenderer = PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY))

            _uiState.update { currentState ->
                currentState.copy(
                    ActualPdf = pdfRenderer
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun decodeBase64ToPdfFile(cacheDir: File, base64String: String): File {
        val pdfBytes = Base64.getDecoder().decode(base64String)
        val pdfFile = File(cacheDir, "temp.pdf")
        try {
            FileOutputStream(pdfFile).use { outputStream ->
                outputStream.write(pdfBytes)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return pdfFile
    }

}