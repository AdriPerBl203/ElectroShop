package com.AG_AP.electroshop.viewModels

import android.content.ContentValues
import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
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
import java.io.InputStream
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
                BusinessPartnerWithInvoiceList = mutableList,
                BusinessPartnerWithInvoiceListBackud = mutableList
            )
        }

    }

    fun cardNameChange(it: String) {

        if(_uiState.value.CardName.length>2){
            _uiState.value.BusinessPartnerWithInvoiceList =mutableListOf()
            _uiState.value.BusinessPartnerWithInvoiceListBackud.forEach{ x ->
                if (x != null) {
                    if(x.CardCode.contains(it)){
                        _uiState.value.BusinessPartnerWithInvoiceList+=x
                    }
                }
            }
        }else{
            _uiState.value.BusinessPartnerWithInvoiceList = _uiState.value.BusinessPartnerWithInvoiceListBackud
        }
        _uiState.update { currentState ->
            currentState.copy(
                CardName = it
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
            _uiState.value.pdfFile = pdfFile
            val pdfRenderer = PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY))

            _uiState.update { currentState ->
                currentState.copy(
                    ActualPdf = pdfRenderer
                )
            }
        }
    }

    fun savePDF(){
        val context = ObjectContext.context // Reemplaza `yourContext` con el contexto adecuado
        val pdfFile = _uiState.value.pdfFile
        if (pdfFile == null) {
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, pdfFile.name)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
            }

            val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

            uri?.let {
                resolver.openOutputStream(it).use { outputStream ->
                    pdfFile.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream!!)
                    }
                }
            }
        } else {
            val documentsFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "")
            if (!documentsFolder.exists()) {
                documentsFolder.mkdirs()
            }
            val newPdfFile = File(documentsFolder, pdfFile.name)
            try {
                pdfFile.inputStream().use { input ->
                    FileOutputStream(newPdfFile).use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
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