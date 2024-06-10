package com.AG_AP.electroshop.viewModels

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.AG_AP.electroshop.realm.InvoiceDataCRUD
import com.AG_AP.electroshop.realm.models.InvoiceData
import com.AG_AP.electroshop.functions.ActivityObj
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
                BusinessPartnerWithInvoiceList = mutableList,
                BusinessPartnerWithInvoiceListBackud = mutableList
            )
        }

    }

    fun cardNameChange(it: String) {

        if (_uiState.value.CardName.length > 2) {
            _uiState.value.BusinessPartnerWithInvoiceList = mutableListOf()
            _uiState.value.BusinessPartnerWithInvoiceListBackud.forEach { x ->
                if (x != null) {
                    if (x.CardCode.contains(it)) {
                        _uiState.value.BusinessPartnerWithInvoiceList += x
                    }
                }
            }
        } else {
            _uiState.value.BusinessPartnerWithInvoiceList =
                _uiState.value.BusinessPartnerWithInvoiceListBackud
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
            _uiState.value.itemActual = item
            // Actualizamos el base64
            _uiState.update { currentState ->
                currentState.copy(
                    ActualCardCode = item.CardCode,
                    Base64String = item.Base64String
                )
            }

            val pdfFile =
                decodeBase64ToPdfFile(ObjectContext.context.cacheDir, _uiState.value.Base64String)
            _uiState.value.pdfFile = pdfFile
            val pdfRenderer =
                PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY))

            _uiState.update { currentState ->
                currentState.copy(
                    ActualPdf = pdfRenderer
                )
            }
        }
    }

    fun savePDF() {
        //Comprueba primeramente los permisos
        checkPermissions()


        if (ContextCompat.checkSelfPermission(
                ObjectContext.context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val context = ObjectContext.context // Reemplaza `yourContext` con el contexto adecuado
            val pdfFile = _uiState.value.pdfFile
            if (pdfFile == null) {
                return
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val namePDF =
                    _uiState.value.itemActual?.CardCode + "-" + _uiState.value.itemActual?.DocEntry
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, namePDF)
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
                val namePDF =
                    _uiState.value.itemActual?.CardCode + "-" + _uiState.value.itemActual?.DocEntry + ".pdf"
                val documentsFolder = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                    ""
                )
                if (!documentsFolder.exists()) {
                    documentsFolder.mkdirs()
                }
                val newPdfFile = File(documentsFolder, namePDF)
                try {
                    pdfFile.inputStream().use { input ->
                        FileOutputStream(newPdfFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                } catch (e: IOException) {
                    Log.e("Errores", e.stackTraceToString())
                }
            }
        } else {
            Toast.makeText(ObjectContext.context,"Se deben de aceptar los permisos de escritura", Toast.LENGTH_LONG ).show()
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
            Log.e("Errores", e.stackTraceToString())
        }
        return pdfFile
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                ObjectContext.context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //Permiso no aceptado por el momento
            //Solicitamos el permiso
            requestWritePermission()
        } else {
            //El permiso se encuentra ya aceptado
        }
    }

    private fun requestWritePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                ActivityObj.ObjectActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            //El usuario ha rechazado los permisos
        } else {
            //Pedir permiso
            ActivityCompat.requestPermissions(
                ActivityObj.ObjectActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1234
            )
        }
    }


}